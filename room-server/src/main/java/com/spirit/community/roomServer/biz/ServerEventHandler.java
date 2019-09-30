package com.spirit.community.roomServer.biz;

import com.alibaba.fastjson.JSON;
import com.spirit.community.roomServer.common.rpc.constant.RpcEventType;
import com.spirit.community.roomServer.session.Session;
import com.spirit.community.protocol.thrift.common.HelloNotify;
import com.spirit.community.protocol.thrift.common.IceServer;
import com.spirit.community.protocol.thrift.common.SessionTicket;
import com.spirit.community.protocol.thrift.login.*;
import com.spirit.community.roomServer.common.exception.MainStageException;
import com.spirit.community.roomServer.service.UserInfoService;
import com.spirit.community.roomServer.service.dao.entity.UserInfo;
import com.spirit.community.roomServer.session.SessionFactory;
import com.spirit.tba.Exception.TbaException;
import com.spirit.tba.core.*;
import com.spirit.tba.tools.TbaToolsKit;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import static com.spirit.community.roomServer.common.exception.ExceptionCode.*;


@Slf4j
@Component
@Sharable
public class ServerEventHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        long rnd = new Random().nextLong();
        long serverRandom = rnd > 0 ? rnd : rnd * (-1);

        HelloNotify notify = new HelloNotify();
        notify.error_code = 20001;
        notify.setServer_random(serverRandom);
        notify.setService_id(1000);
        notify.setError_text("OK");

        TsRpcHead head = new TsRpcHead(RpcEventType.MT_HELLO_NOTIFY);
        ctx.write(new TbaEvent(head, notify, 1024, false));
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

        if (msg instanceof ClientPasswordLoginReq) {

            ClientPasswordLoginReq entity = (ClientPasswordLoginReq) msg;
            log.info("ClientPasswordLoginReq: {}", JSON.toJSONString(entity, true));

            ClientLoginRes res = new ClientLoginRes();
            try {
                ClientPasswordLoginReqChecksum checksum = new TbaToolsKit<ClientPasswordLoginReqChecksum>().deserialize(entity.getCheck_sum().getBytes("ISO8859-1"), ClientPasswordLoginReqChecksum.class);
                log.info("ClientPasswordLoginReqChecksum: {}", JSON.toJSONString(checksum, true));

                Session session = sessionFactory.getSessionById(ctx.channel().id().asLongText());
                if (session.getServerRandom() != checksum.getServer_random()) {
                    res.error_code = Short.valueOf(SERVER_RANDOM_INVALID.code());
                    res.error_text = SERVER_RANDOM_INVALID.text();
                    TsRpcHead head = new TsRpcHead(RpcEventType.MT_CLIENT_LOGIN_RES);
                    ctx.write(new TbaEvent(head, res, 1024, true));
                    ctx.flush();
                    return;
                }

                userInfoService.identity(entity.user_id, checksum.password);

                sessionFactory.authorized(ctx.channel().id().asLongText());

                res.error_code = Short.valueOf(SUCCESS.code());
                res.error_text = SUCCESS.text();

                SessionTicket ticket = new SessionTicket();
                ticket.signal_server = "https://47.100.251.132";
                IceServer iceServer = new IceServer();
                iceServer.url = "turn:coturn.86bba.com:3478";
                iceServer.user = "spirit";
                iceServer.passwd = "spirit";
                ticket.ice_server = iceServer;

                byte[] sessionTicket = new TbaToolsKit<SessionTicket>().serialize(ticket, 256);
                res.session_ticket = new String(sessionTicket, "ISO8859-1");
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
            ctx.write(new TbaEvent(head, res, 1024, true));
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
            info.setInvitationCode(entity.invitation_code);
            info.setIdentificationCardId(entity.identity_card);

            UserRegisterRes res = new UserRegisterRes();

            if (!StringUtils.equalsIgnoreCase(entity.invitation_code, "sxkj") && !StringUtils.equalsIgnoreCase(entity.invitation_code, "guest")) {
                res.error_code = Short.valueOf(INVITE_CODE_INVALID.code());
                res.error_text = INVITE_CODE_INVALID.text();
            }
            else {
                try {
                    UserInfo s = userInfoService.register(info);
                    res.error_code = 0;
                    res.error_text = "OK";
                    res.user_id =   String.valueOf(s.getUserId());
                }
                catch (Exception e) {
                    res.error_code = Short.valueOf(UNEXPECTED_EXCEPTION.code());
                    res.error_text = UNEXPECTED_EXCEPTION.text();
                }
            }

            TsRpcHead head = new TsRpcHead(RpcEventType.MT_CLIENT_REGISTER_RES);
            ctx.write(new TbaEvent(head, res, 1024, false));
            ctx.flush();
        }



    }



}
