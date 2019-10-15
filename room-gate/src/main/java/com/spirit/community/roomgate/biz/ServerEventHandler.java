package com.spirit.community.roomgate.biz;

import com.alibaba.fastjson.JSON;
import com.spirit.community.common.constant.RpcEventType;
import com.spirit.community.common.exception.MainStageException;
import com.spirit.community.common.pojo.RoomGateInfo;
import com.spirit.community.common.pojo.RoomgateUser;
import com.spirit.community.protocol.thrift.common.CommonRes;
import com.spirit.community.protocol.thrift.common.HelloNotify;
import com.spirit.community.protocol.thrift.roomgate.*;
import com.spirit.community.roomgate.redis.RedisUtil;
import com.spirit.community.roomgate.relay.RelayManager;
import com.spirit.community.roomgate.relay.RelayProxy;
import com.spirit.community.roomgate.service.RoomGateInfoService;
import com.spirit.community.roomgate.session.Session;
import com.spirit.community.roomgate.session.SessionFactory;
import com.spirit.tba.Exception.TbaException;
import com.spirit.tba.core.*;
import com.spirit.tba.tools.TbaToolsKit;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import static com.spirit.community.common.exception.ExceptionCode.*;


@Slf4j
@Sharable
@Component
public class ServerEventHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RoomGateInfoService roomGateInfoService;

    @Autowired
    private RelayManager relayManager;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        long rnd = new Random().nextLong();
        long serverRandom = rnd > 0 ? rnd : rnd * (-1);

        HelloNotify notify = new HelloNotify();
        notify.error_code = 20001;
        notify.setServer_random(serverRandom);
        notify.setService_id(Integer.valueOf(roomGateInfoService.getRoomGateInfo().getRoomGateId()));
        notify.setError_text("OK");

        TsRpcHead head = new TsRpcHead(RpcEventType.MT_HELLO_NOTIFY);
        ctx.write(new TbaEvent(head, notify, 1024, EncryptType.DISABLE));
        ctx.flush();

        Session session = new Session(ctx, serverRandom);

        sessionFactory.addSession(session);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        sessionFactory.removeById(ctx.channel().id().asLongText());
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        if (msg instanceof ConnectReq) {

            ConnectReq entity = (ConnectReq) msg;
            log.info("ConnectReq: {}", JSON.toJSONString(entity, true));

            CommonRes res = new CommonRes();

            try {
                ConnectChecksum checksum = new TbaToolsKit<ConnectChecksum>().deserialize(entity.getChecksum().getBytes("ISO8859-1"), ConnectChecksum.class);
                log.info("ConnectChecksum: {}", JSON.toJSONString(checksum, true));

                Session session = sessionFactory.getSessionByChannelId(ctx.channel().id().asLongText());
                if (session.getServerRandom() != checksum.getServer_random()) {
                    res.error_code = Short.valueOf(SERVER_RANDOM_INVALID.code());
                    res.error_text = SERVER_RANDOM_INVALID.text();
                    TsRpcHead head = new TsRpcHead(RpcEventType.CONNECT_REQ);
                    ctx.write(new TbaEvent(head, res, 256, EncryptType.WHOLE));
                    ctx.flush();
                    return;
                }

                sessionFactory.authorized(ctx.channel().id().asLongText(), checksum.getUser_id());

                RoomgateUser user = new RoomgateUser();
                user.setUid(checksum.getUser_id());
                user.setRoomGateInfo(roomGateInfoService.getRoomGateInfo());
                redisUtil.set(String.valueOf(checksum.getUser_id()), user);

                res.error_code = Short.valueOf(SUCCESS.code());
                res.error_text = SUCCESS.text();
            } catch (IllegalAccessException | InstantiationException | UnsupportedEncodingException | TbaException e) {
                log.error(e.getLocalizedMessage(), e);
                res.error_code = Short.valueOf(UNEXPECTED_EXCEPTION.code());
                res.error_text = UNEXPECTED_EXCEPTION.text();
            }

            TsRpcHead head = new TsRpcHead(RpcEventType.CONNECT_RES);
            ctx.write(new TbaEvent(head, res, 256, EncryptType.WHOLE));
            ctx.flush();
        }
        else if (msg instanceof RelayProxy) {

            RelayProxy proxy = (RelayProxy) msg;
            TsRpcHead header = proxy.getHead();

            long srcUid = header.GetAttach1() | header.GetAttach2() << 32;
            long destUid = header.GetAttach3() | header.GetAttach4() << 32;

            RoomgateUser destUser = (RoomgateUser) redisUtil.get(String.valueOf(destUid));
            RoomgateUser srcUser = (RoomgateUser) redisUtil.get(String.valueOf(srcUid));

            if (destUser != null) {

                String rid = destUser.getRoomGateInfo().getRoomGateId();
                String localRid = roomGateInfoService.getRoomGateInfo().getRoomGateId();

                if (destUser.getRoomGateInfo().getRoomGateId().equalsIgnoreCase(roomGateInfoService.getRoomGateInfo().getRoomGateId())) {
                    Session session = sessionFactory.getSessionByUid(destUid);
                    if (session != null) {
                        header.SetType((short) RpcEventType.ROOMGATE_CHAT_NOTIFY);
                        session.getChannel().writeAndFlush(new TbaEvent(header, proxy, 512, EncryptType.BODY));
                    }
                }
                else {
                    RoomGateInfo destRoomgateInfo = destUser.getRoomGateInfo();
                    Session session = null;
                    try {
                        if (relayManager.isConnect(destRoomgateInfo.getRoomGateId())) {
                            relayManager.putData(destRoomgateInfo.getRoomGateId(), proxy);
                        }
                        else if ((session = sessionFactory.getByRoomgateId(destRoomgateInfo.getRoomGateId())) != null) {
                            //header.SetType((short) RpcEventType.ROOMGATE_CHAT_RELAY);
                            header.SetType((short) RpcEventType.ROOMGATE_CHAT_RELAY);
                            session.getChannel().writeAndFlush(new TbaEvent(header, proxy, 512, EncryptType.BODY));
                        }
                        else {
                            relayManager.openRoomGate(destRoomgateInfo.getIp(), destRoomgateInfo.getPort(), destRoomgateInfo.getRoomGateId(), proxy);
                        }

                    } catch (MainStageException e) {
                        log.error(e.getLocalizedMessage(), e);
                    }
                }
            }
            else {
                //写入数据库
            }
        }
        else if (msg instanceof RoomgateConnectReq) {
            RoomgateConnectReq entity = (RoomgateConnectReq) msg;
            CommonRes res = new CommonRes();
            RoomgateConnectChecksum checksum = null;
            try {
                checksum = new TbaToolsKit<RoomgateConnectChecksum>().deserialize(entity.checksum.getBytes("ISO8859-1"), RoomgateConnectChecksum.class);
                Session session = sessionFactory.getRoomGateSessionByChannelId(ctx.channel().id().asLongText());
                if (session.getServerRandom().longValue() == checksum.server_random) {
                    session.setRoomgateId(checksum.roomgate_id);
                    session.setRelayChannel(true);
                    //sessionFactory.addRoomGateSession(session);
                    res.error_code = 0;
                    res.error_text = "OK";
                }
                else {
                    sessionFactory.removeById(ctx.channel().id().asLongText());
                    res.error_code = Short.valueOf(ROOMGATE_CONNECT_FAILED.code());
                    res.error_text = ROOMGATE_CONNECT_FAILED.text();
                }
            } catch (IllegalAccessException | TbaException | InstantiationException | UnsupportedEncodingException e) {
                log.error(e.getLocalizedMessage(), e);
                res.error_code = Short.valueOf(ROOMGATE_CONNECT_FAILED.code());
                res.error_text = ROOMGATE_CONNECT_FAILED.text();
            }

            TsRpcHead head = new TsRpcHead(RpcEventType.ROOMGATE_CONNECT_RES);
            ctx.write(new TbaEvent(head, res, 256, EncryptType.BODY));
            ctx.flush();
        }



    }



}
