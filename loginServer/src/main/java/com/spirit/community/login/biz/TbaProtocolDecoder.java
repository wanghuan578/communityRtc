package com.spirit.community.login.biz;

import java.util.List;
import com.spirit.community.protocol.thrift.login.ClientPasswordLoginReq;
import com.spirit.community.protocol.thrift.login.UserRegisterReq;
import com.spirit.tba.Exception.TbaException;
import com.spirit.tba.core.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;




@Slf4j
public class TbaProtocolDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // TODO Auto-generated method stub

        while (in.readableBytes() > 4) {

            int msg_len = in.readInt();
            short flag = in.readShort();

            TsRpcByteBuffer msg = null;

            if(flag == Encrypt.TYPE_ENABLE) {

                byte[] encrypt = new byte[msg_len - 4];
                for (int i = 0; i < msg_len - 4; i++) {
                    encrypt[i] = in.readByte();
                }

                String key = "123";
                //String original = TbaAes.decrypt(new String(encrypt, "utf-8"), "123");
                String original = TbaAes.decode(new String(encrypt, "utf-8"), key);
                byte[] msg00 = original.getBytes("ISO8859-1");
                msg = new TsRpcByteBuffer(msg00, msg00.length);
            }
            else {
                msg = new TsRpcByteBuffer(msg_len);
                msg.WriteI32(msg_len);
                msg.WriteI16(flag);
                for (int i = 0; i < msg_len - 6; i++) {
                    msg.WriteByte(in.readByte());
                }
            }

            TsRpcEventParser parser = new TsRpcEventParser(msg);
            TsRpcHead header = parser.Head();

            log.info("Msg Type: {}", header.GetType());

            try {
                switch (header.GetType()) {

                    case RpcEventType.MT_CLIENT_PASSWORD_LOGIN_REQ: {
                        TsRpcProtocolFactory<ClientPasswordLoginReq> protocol = new TsRpcProtocolFactory<ClientPasswordLoginReq>(msg);
                        out.add(protocol.Decode(ClientPasswordLoginReq.class));
                    }
                        break;

                    case RpcEventType.MT_CLIENT_REGISTER_REQ: {
                        TsRpcProtocolFactory<UserRegisterReq> protocol = new TsRpcProtocolFactory<UserRegisterReq>(msg);
                        out.add(protocol.Decode(UserRegisterReq.class));
                    }
                    break;


                    default:
                        break;
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
