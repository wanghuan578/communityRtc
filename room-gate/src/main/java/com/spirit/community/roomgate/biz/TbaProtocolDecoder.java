package com.spirit.community.roomgate.biz;

import java.util.List;
import com.spirit.community.common.constant.Encrypt;
import com.spirit.community.common.constant.RpcEventType;
import com.spirit.community.common.pojo.RoomgateUser;
import com.spirit.community.protocol.thrift.roomgate.ConnectReq;
import com.spirit.community.roomgate.context.ApplicationContextUtils;
import com.spirit.community.roomgate.redis.RedisUtil;
import com.spirit.community.roomgate.relay.RelayManager;
import com.spirit.community.roomgate.session.Session;
import com.spirit.community.roomgate.session.SessionFactory;
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
            byte[] relay = null;

            if(flag == Encrypt.TYPE_ENABLE) {

                byte[] encrypt = new byte[msg_len - 6];
                for (int i = 0; i < msg_len - 6; i++) {
                    encrypt[i] = in.readByte();
                }

                SessionFactory factory = ApplicationContextUtils.getBean(SessionFactory.class);
                Session session = factory.getSessionByChannelId(ctx.channel().id().asLongText());
                String serverRandom = String.valueOf(session.getServerRandom());
                log.info("decrypt key: {}", serverRandom);
                String original = TbaAes.decode(new String(encrypt, "utf-8"), serverRandom);
                relay = original.getBytes("ISO8859-1");
                msg = new TsRpcByteBuffer(relay, relay.length);
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

            log.info("msg receive type: {}", header.GetType());

            try {
                if (header.GetType() == RpcEventType.ROOMGATE_CONNECT_REQ) {
                    TsRpcProtocolFactory<ConnectReq> protocol = new TsRpcProtocolFactory<ConnectReq>(msg);
                    out.add(protocol.Decode(ConnectReq.class));
                }
                else if (header.GetType() == RpcEventType.ROOMGATE_CHAT_REQ){

                    long suid = header.GetAttach1() | header.GetAttach2() << 32;
                    long duid = header.GetAttach3() | header.GetAttach4() << 32;

                    RelayManager relayManager = ApplicationContextUtils.getBean(RelayManager.class);
                    relayManager.relayMessage((long)suid, relay);
                }
                else {

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
