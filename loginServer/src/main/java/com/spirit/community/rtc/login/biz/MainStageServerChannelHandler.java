package com.spirit.community.rtc.login.biz;

import com.alibaba.fastjson.JSON;
import com.spirit.community.rtc.login.common.exception.MainStageException;
import com.spirit.community.rtc.login.protocol.rpc.thrift.*;
import com.spirit.community.rtc.login.service.UserInfoService;
import com.spirit.community.rtc.login.service.dao.entity.UserInfo;
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

import static com.spirit.community.rtc.login.common.exception.ExceptionCode.*;


@Slf4j
@Component
@Sharable
public class MainStageServerChannelHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private UserInfoService userInfoService;

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

        TsRpcHead head = new TsRpcHead(RpcEventType.MT_HELLO_NOTIFY);
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

            ClientPasswordLoginReq entity = (ClientPasswordLoginReq) msg;
            log.info("ClientPasswordLoginReq: {}", JSON.toJSONString(entity, true));

            ClientLoginRes res = new ClientLoginRes();
            try {
                ClientPasswordLoginReqChecksum checksum = new TbaUtil<ClientPasswordLoginReqChecksum>().Deserialize(entity.getCheck_sum().getBytes("ISO8859-1"), ClientPasswordLoginReqChecksum.class);
                log.info("ClientPasswordLoginReqChecksum: {}", JSON.toJSONString(checksum, true));
                userInfoService.identity(entity.user_id, checksum.password);
            } catch (IllegalAccessException | InstantiationException | UnsupportedEncodingException | TbaException e) {
                log.error(e.getLocalizedMessage(), e);
                res.error_code = Short.valueOf(UNEXPECTED_EXCEPTION.code());
                res.error_text = UNEXPECTED_EXCEPTION.text();
            } catch (MainStageException e) {
                log.error("MainStageException", e);
                if (USERINFO_NOT_EXIST == e.getResultType()) {
                    res.error_code = Short.valueOf(USERINFO_NOT_EXIST.code());
                    res.error_text = USERINFO_NOT_EXIST.text();
                }
                if (USERID_OR_PASSWD_INVALID == e.getResultType()) {
                    res.error_code = Short.valueOf(USERID_OR_PASSWD_INVALID.code());
                    res.error_text = USERID_OR_PASSWD_INVALID.text();
                }
            }

            TsRpcHead head = new TsRpcHead(RpcEventType.MT_CLIENT_LOGIN_RES);
            ctx.write(new TsEvent(head, res, 1024));
            ctx.flush();
        }
        if (msg instanceof UserRegisterReq) {

            UserRegisterReq entity = (UserRegisterReq) msg;
            log.info("UserRegisterReq: {}", JSON.toJSONString(entity, true));

            UserInfo info = new UserInfo();
            info.setUserName(entity.getUser_name());
            info.setNickName(entity.getNick_name());
            info.setPassword(entity.getPassword());
            info.setGender(entity.getGender());

            UserRegisterRes res = new UserRegisterRes();

            try {
                userInfoService.register(info);
                res.error_code = 0;
                res.error_text = "OK";
            }
            catch (Exception e) {
                res.error_code = Short.valueOf(UNEXPECTED_EXCEPTION.code());
                res.error_text = UNEXPECTED_EXCEPTION.text();
            }

            TsRpcHead head = new TsRpcHead(RpcEventType.MT_CLIENT_REGISTER_RES);
            ctx.write(new TsEvent(head, res, 1024));
            ctx.flush();
        }



    }



}
