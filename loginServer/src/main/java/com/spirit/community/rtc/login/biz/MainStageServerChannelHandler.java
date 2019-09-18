package com.spirit.community.rtc.login.biz;

import com.alibaba.fastjson.JSON;
import com.spirit.community.rtc.login.protocol.rpc.thrift.ClientLoginRes;
import com.spirit.community.rtc.login.protocol.rpc.thrift.ClientPasswordLoginReq;
import com.spirit.community.rtc.login.protocol.rpc.thrift.HelloNotify;
import com.spirit.community.rtc.login.session.SessionFactory;
import com.spirit.tba.core.TsEvent;
import com.spirit.tba.core.TsRpcHead;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Slf4j
@Component
@Sharable
public class MainStageServerChannelHandler extends ChannelInboundHandlerAdapter {

//    @Autowired
//    private ProviderService providerService;
//
//    @Autowired
//    private ComsumerService comsumerService;
//
//    @Autowired
//    private ServiceInfoSync serviceInfoSync;

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        HelloNotify body = new HelloNotify();
        body.setServer_random(1000000);
        body.setService_id(888888);
        body.setError_code((short)0);
        body.setError_text("OK");

        TsRpcHead head = new TsRpcHead(RpcEventType.MT_COMMON_HELLO_NOTIFY);
        ctx.write(new TsEvent(head, body, 1024));
        ctx.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        String path;
//        if (StringUtils.isNotEmpty(path = sessionFactory.remove(ctx))) {
//
//        }
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        if (msg instanceof ClientPasswordLoginReq) {

            log.info("ClientPasswordLoginReq: {}", JSON.toJSONString(msg, true));

            ClientPasswordLoginReq entity = (ClientPasswordLoginReq) msg;

            //todo

            ClientLoginRes body = new ClientLoginRes();
            body.error_code = 0;
            body.error_text = "OK";
            body.session_ticket = "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW";

            TsRpcHead head = new TsRpcHead(RpcEventType.MT_CLIENT_LOGIN_RES);
            ctx.write(new TsEvent(head, body, 1024));
            ctx.flush();
        }



    }



}
