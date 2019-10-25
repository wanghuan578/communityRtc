package com.spirit.community.login.biz;

import java.util.List;
import com.spirit.community.common.constant.RpcEventType;
import com.spirit.community.login.context.ApplicationContextUtils;
import com.spirit.community.login.session.Session;
import com.spirit.community.login.session.SessionFactory;
import com.spirit.community.protocol.thrift.login.ClientPasswordLoginReq;
import com.spirit.community.protocol.thrift.login.UserRegisterReq;
import com.spirit.tba.Exception.TbaException;
import com.spirit.tba.core.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TbaProtocolDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // TODO Auto-generated method stub

        while (in.readableBytes() > 4) {

            int msg_len = in.readInt();
            short flag = in.readShort();

            TsRpcByteBuffer msg = null;

            if(flag == EncryptType.WHOLE) {

                byte[] encrypt = new byte[msg_len - TsMagic.MAGIC_OFFSET];
                for (int i = 0; i < msg_len - TsMagic.MAGIC_OFFSET; i++) {
                    encrypt[i] = in.readByte();
                }

                SessionFactory factory = ApplicationContextUtils.getBean(SessionFactory.class);
                Session session = factory.getSessionById(ctx.channel().id().asLongText());
                String serverRandom = String.valueOf(session.getServerRandom());
                log.info("decrypt key: {}", serverRandom);
                String original = TbaAes.decode(new String(encrypt, "utf-8"), serverRandom);
                byte[] msg00 = original.getBytes("ISO8859-1");
                msg = new TsRpcByteBuffer(msg00, msg00.length);
            }
            else {
                msg = new TsRpcByteBuffer(msg_len);
                msg.writeI32(msg_len);
                msg.writeI16(flag);
                for (int i = 0; i < msg_len - 6; i++) {
                    msg.writeByte(in.readByte());
                }
            }

            TsRpcEventParser parser = new TsRpcEventParser(msg);
            TsRpcHead header = parser.Head();

            log.info("msg receive type: {}", header.getType());

            try {
                if (RpcEventType.MT_CLIENT_PASSWORD_LOGIN_REQ == header.getType()) {
                    TsRpcProtocolFactory<ClientPasswordLoginReq> protocol = new TsRpcProtocolFactory<ClientPasswordLoginReq>(msg);
                    out.add(protocol.Decode(ClientPasswordLoginReq.class));
                }
                else if (RpcEventType.MT_CLIENT_REGISTER_REQ == header.getType()) {
                    TsRpcProtocolFactory<UserRegisterReq> protocol = new TsRpcProtocolFactory<UserRegisterReq>(msg);
                    out.add(protocol.Decode(UserRegisterReq.class));
                }
            }
            catch(TbaException e){
                log.error(e.getLocalizedMessage(), e);
            }
            catch(InstantiationException e){
                log.error(e.getLocalizedMessage(), e);
            }
            catch(IllegalAccessException e){
                log.error(e.getLocalizedMessage(), e);
            }
        }
    }

        


}
