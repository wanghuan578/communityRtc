package com.spirit.community.roomgate.biz;

import java.util.List;
import com.spirit.community.common.constant.RpcEventType;
import com.spirit.community.protocol.thrift.roomgate.ConnectReq;
import com.spirit.community.protocol.thrift.roomgate.RoomgateConnectReq;
import com.spirit.community.roomgate.context.ApplicationContextUtils;
import com.spirit.community.roomgate.relay.session.RelayProtocol;
import com.spirit.community.roomgate.session.Session;
import com.spirit.community.roomgate.session.SessionFactory;
import com.spirit.tba.Exception.TbaException;
import com.spirit.tba.core.*;
import com.spirit.tba.tools.TbaHeadUtil;
import com.spirit.tba.tools.TbaToolsKit;
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

            byte [] magic = new byte[TsMagic.MAGIC_OFFSET];
            for (int i = 0; i < TsMagic.MAGIC_OFFSET; i++) {
                magic[i] = in.readByte();
            }
            TsMagic magicHead = TbaHeadUtil.preParser(magic);

            int msg_len = magicHead.getLength();
            short flag = magicHead.getFlag();

            TsRpcByteBuffer msg = null;
            byte[] relay = null;
            TsRpcHead header = null;

            if(flag == EncryptType.WHOLE) {

                byte[] encrypt = new byte[msg_len - TsMagic.MAGIC_OFFSET];
                for (int i = 0; i < msg_len - TsMagic.MAGIC_OFFSET; i++) {
                    encrypt[i] = in.readByte();
                }

                SessionFactory factory = ApplicationContextUtils.getBean(SessionFactory.class);
                Session session = factory.getSessionByChannelId(ctx.channel().id().asLongText());
                String serverRandom = String.valueOf(session.getServerRandom());
                log.info("decrypt key: {}", serverRandom);
                String original = TbaAes.decode(new String(encrypt, "utf-8"), serverRandom);
                relay = original.getBytes("ISO8859-1");
                msg = new TsRpcByteBuffer(relay, relay.length);

                TsRpcEventParser parser = new TsRpcEventParser(msg);
                header = parser.Head();
            }
            else if (flag == EncryptType.BODY) {

                log.info("encrypt type: {}", flag);

                byte[] all = new byte[TbaHeadUtil.HEAD_SIZE];
                System.arraycopy(magic, 0 , all, 0, TsMagic.MAGIC_OFFSET);
                for (int i = TsMagic.MAGIC_OFFSET; i < TbaHeadUtil.HEAD_SIZE; i++) {
                    all[i] = in.readByte();
                }

                byte[] encryptData = new byte[msg_len - TbaHeadUtil.HEAD_SIZE];
                for (int i = 0; i < msg_len - TbaHeadUtil.HEAD_SIZE; i++) {
                    encryptData[i] = in.readByte();
                }

                header = TbaHeadUtil.parser(all);

                if (header.getType() == RpcEventType.ROOMGATE_CHAT_RELAY) {

                    SessionFactory factory = ApplicationContextUtils.getBean(SessionFactory.class);
                    Session session = factory.getSessionByChannelId(ctx.channel().id().asLongText());
                    RelayProtocol proxy = new RelayProtocol();
                    proxy.setHead(header);
                    proxy.setData(encryptData);
                    out.add(proxy);

                }
                else if (header.getType() == RpcEventType.ROOMGATE_CHAT_REQ) {

                    SessionFactory factory = ApplicationContextUtils.getBean(SessionFactory.class);
                    Session session = factory.getSessionByChannelId(ctx.channel().id().asLongText());
                    RelayProtocol proxy = new RelayProtocol();
                    proxy.setHead(header);
                    proxy.setData(encryptData);
                    out.add(proxy);

                }
                else if (header.getType() == RpcEventType.ROOMGATE_CONNECT_REQ) {
                    SessionFactory factory = ApplicationContextUtils.getBean(SessionFactory.class);
                    Session session = factory.getSessionByChannelId(ctx.channel().id().asLongText());
                    String serverRandom = String.valueOf(session.getServerRandom());
                    log.info("decrypt key: {}", serverRandom);
                    String original = TbaAes.decode(new String(encryptData, "utf-8"), serverRandom);

                    RoomgateConnectReq req = new TbaToolsKit<RoomgateConnectReq>().deserialize(original.getBytes("ISO8859-1"), RoomgateConnectReq.class);
                    out.add(req);
                }

                return;
            }
            else {
                msg = new TsRpcByteBuffer(msg_len);
                msg.writeI32(msg_len);
                msg.writeI16(flag);
                for (int i = 0; i < msg_len - 6; i++) {
                    msg.writeByte(in.readByte());
                }

                TsRpcEventParser parser = new TsRpcEventParser(msg);
                header = parser.Head();
            }

            log.info("msg receive type: {}", header.getType());

            try {
                if (header.getType() == RpcEventType.CONNECT_REQ) {
                    TsRpcProtocolFactory<ConnectReq> protocol = new TsRpcProtocolFactory<ConnectReq>(msg);
                    out.add(protocol.Decode(ConnectReq.class));
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
