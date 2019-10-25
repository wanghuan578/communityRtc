package com.spirit.community.roomgate.relay.biz;

import com.alibaba.fastjson.JSON;
import com.spirit.community.common.constant.RpcEventType;
import com.spirit.community.common.exception.MainStageException;
import com.spirit.community.common.pojo.RoomGateInfo;
import com.spirit.community.common.pojo.RoomgateUser;
import com.spirit.community.protocol.thrift.common.CommonRes;
import com.spirit.community.protocol.thrift.common.HelloNotify;
import com.spirit.community.protocol.thrift.roomgate.RoomgateConnectChecksum;
import com.spirit.community.protocol.thrift.roomgate.RoomgateConnectReq;
import com.spirit.community.roomgate.context.ApplicationContextUtils;
import com.spirit.community.roomgate.redis.RedisUtil;
import com.spirit.community.roomgate.relay.proxy.RelayMsgProxy;
import com.spirit.community.roomgate.relay.session.RelayManager;
import com.spirit.community.roomgate.relay.session.RelayProtocol;
import com.spirit.community.roomgate.service.RoomGateInfoService;
import com.spirit.community.roomgate.session.Session;
import com.spirit.community.roomgate.session.SessionFactory;
import com.spirit.tba.core.EncryptType;
import com.spirit.tba.core.TbaEvent;
import com.spirit.tba.core.TsRpcHead;
import com.spirit.tba.tools.TbaToolsKit;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class RelayEventHandler extends SimpleChannelInboundHandler {

    private String roomGateId;

    public RelayEventHandler(String serverId) {
        this.roomGateId = serverId;
    }

    public String getServerId() {
        return roomGateId;
    }

    public void setServerId(String roomGateId) {
        this.roomGateId = roomGateId;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionFactory factory = ApplicationContextUtils.getBean(SessionFactory.class);
        Session session = factory.removeById(ctx.channel().id().asLongText());

        RelayManager relayManager = ApplicationContextUtils.getBean(RelayManager.class);
        relayManager.close(session.getRoomgateId());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object o) throws Exception {

        if (o instanceof HelloNotify) {

            HelloNotify notify = (HelloNotify) o;
            System.out.println(JSON.toJSONString(notify, true));

            SessionFactory factory = ApplicationContextUtils.getBean(SessionFactory.class);
            Session roomgeteSesion = new Session(ctx, notify.server_random, String.valueOf(notify.service_id));
            factory.addSession(roomgeteSesion);

            long rnd = new Random().nextLong();
            long clientRandom = rnd > 0 ? rnd : rnd * (-1);

            RoomgateConnectChecksum checksum = new RoomgateConnectChecksum();
            checksum.client_random = clientRandom;
            checksum.server_random = notify.server_random;
            RoomGateInfoService roomGateInfoService = ApplicationContextUtils.getBean(RoomGateInfoService.class);
            checksum.roomgate_id = roomGateInfoService.getRoomGateInfo().getRoomGateId();

            RoomgateConnectReq req = new RoomgateConnectReq();
            byte[] data = new TbaToolsKit<RoomgateConnectChecksum>().serialize(checksum, 512);
            req.checksum = new String(data, "ISO8859-1");

            TsRpcHead head = new TsRpcHead(RpcEventType.ROOMGATE_CONNECT_REQ);
            ctx.write(new TbaEvent(head, req, 512, EncryptType.BODY));
            ctx.flush();


        } else if (o instanceof CommonRes) {
            CommonRes res = (CommonRes) o;
            System.out.println(JSON.toJSONString(res, true));
            SessionFactory factory = ApplicationContextUtils.getBean(SessionFactory.class);
            Session session = factory.getRoomGateSessionByChannelId(ctx.channel().id().asLongText());

            RelayManager relayManager = ApplicationContextUtils.getBean(RelayManager.class);

            RelayMsgProxy<RelayProtocol> c = relayManager.getRelayClientByRoomgateId(session.getRoomgateId());
            c.setAuth(true);
        } else if (o instanceof RelayProtocol) {

            RelayManager relayManager = ApplicationContextUtils.getBean(RelayManager.class);
            SessionFactory sessionFactory = ApplicationContextUtils.getBean(SessionFactory.class);
            RedisUtil redisUtil = ApplicationContextUtils.getBean(RedisUtil.class);
            RoomGateInfoService roomGateInfoService = ApplicationContextUtils.getBean(RoomGateInfoService.class);

            RelayProtocol proxy = (RelayProtocol) o;
            TsRpcHead header = proxy.getHead();

            long srcUid = header.GetAttach1() | header.GetAttach2() << 32;
            long destUid = header.GetAttach3() | header.GetAttach4() << 32;

            RoomgateUser user = (RoomgateUser) redisUtil.get(String.valueOf(destUid));

            if (user != null) {

                String rid = user.getRoomGateInfo().getRoomGateId();
                String localRid = roomGateInfoService.getRoomGateInfo().getRoomGateId();

                if (user.getRoomGateInfo().getRoomGateId().equalsIgnoreCase(roomGateInfoService.getRoomGateInfo().getRoomGateId())) {
                    Session session = sessionFactory.getSessionByUid(destUid);
                    if (session != null) {
                        header.SetType((short) RpcEventType.ROOMGATE_CHAT_NOTIFY);
                        session.getChannel().writeAndFlush(new TbaEvent(header, proxy, 512, EncryptType.BODY));
                    }
                } else {
                    RoomGateInfo info = user.getRoomGateInfo();
                    Session session = null;
                    try {
                        if (relayManager.isConnect(info.getRoomGateId())) {
                            relayManager.putData(info.getRoomGateId(), proxy);
                        }
                        else if ((session = sessionFactory.getByRoomgateId(info.getRoomGateId())) != null) {
                            //header.SetType((short) RpcEventType.ROOMGATE_CHAT_RELAY);
                            header.SetType((short) RpcEventType.ROOMGATE_CHAT_RELAY);
                            session.getChannel().writeAndFlush(new TbaEvent(header, proxy, 512, EncryptType.BODY));
                        } else {
                            relayManager.openRoomGate(info.getIp(), info.getPort(), info.getRoomGateId(), proxy);
                        }

                    } catch (MainStageException e) {
                        log.error(e.getLocalizedMessage(), e);
                    }
                }
            } else {
                //写入数据库
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

    }
}
