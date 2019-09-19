package com.spirit.community.rtc.login.biz;

import com.alibaba.fastjson.JSON;
import com.spirit.community.rtc.login.protocol.rpc.thrift.ClientLoginRes;
import com.spirit.community.rtc.login.protocol.rpc.thrift.ClientPasswordLoginReq;
import com.spirit.community.rtc.login.protocol.rpc.thrift.ClientPasswordLoginReqChecksum;
import com.spirit.community.rtc.login.protocol.rpc.thrift.HelloNotify;
import com.spirit.community.rtc.login.session.SessionFactory;
import com.spirit.tba.Exception.TbaException;
import com.spirit.tba.core.TsEvent;
import com.spirit.tba.core.TsRpcHead;
import com.spirit.tba.utils.TbaUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Random;


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

        long serverRandom = new Random().nextLong();

        HelloNotify body = new HelloNotify();
        body.setServer_random(serverRandom);
        body.setService_id(1000);
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

            try {
                ClientPasswordLoginReqChecksum checksum = new TbaUtil<ClientPasswordLoginReqChecksum>().Deserialize(entity.getCheck_sum().getBytes("ISO8859-1"), ClientPasswordLoginReqChecksum.class);
                log.info("ClientPasswordLoginReqChecksum: {}", JSON.toJSONString(checksum, true));

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (TbaException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

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
