package com.spirit.community.roomgate.relay;

import com.alibaba.fastjson.JSON;
import com.spirit.community.common.constant.RpcEventType;
import com.spirit.community.protocol.thrift.common.HelloNotify;
import com.spirit.community.protocol.thrift.login.ClientPasswordLoginReq;
import com.spirit.community.protocol.thrift.roomgate.ConnectReq;
import com.spirit.community.protocol.thrift.roomgate.RoomgateConnectChecksum;
import com.spirit.community.protocol.thrift.roomgate.RoomgateConnectReq;
import com.spirit.community.roomgate.context.ApplicationContextUtils;
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

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object o) throws Exception {

        if (o instanceof HelloNotify) {

            HelloNotify notify = (HelloNotify) o;
            System.out.println(JSON.toJSONString(notify, true));

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

            TsRpcHead head = new TsRpcHead(RpcEventType.CONNECT_REQ);
            ctx.write(new TbaEvent(head, req, 512, EncryptType.BODY));
            ctx.flush();

            SessionFactory factory = ApplicationContextUtils.getBean(SessionFactory.class);
            Session roomgeteSesion = new Session(ctx, notify.server_random, String.valueOf(notify.service_id));
            factory.addSession(roomgeteSesion);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

    }
}
