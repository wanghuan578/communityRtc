package com.spirit.community.roomgate.relay.biz;

import com.spirit.community.common.constant.RpcEventType;
import com.spirit.community.protocol.thrift.common.CommonRes;
import com.spirit.community.protocol.thrift.common.HelloNotify;
import com.spirit.community.roomgate.context.ApplicationContextUtils;
import com.spirit.community.roomgate.relay.session.RelayProtocol;
import com.spirit.community.roomgate.session.Session;
import com.spirit.community.roomgate.session.SessionFactory;
import com.spirit.tba.exception.TbaException;
import com.spirit.tba.core.*;
import com.spirit.tba.tools.TbaAesUtils;
import com.spirit.tba.tools.TbaHeadUtil;
import com.spirit.tba.tools.TbaSerializeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
public class RelayMsgDecoder extends ByteToMessageDecoder {

    private String roomGateId;

    public RelayMsgDecoder(String serverId) {
        this.roomGateId = serverId;
    }

    public String getServerId() {
        return roomGateId;
    }

    public void setServerId(String roomGateId) {
        this.roomGateId = roomGateId;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // TODO Auto-generated method stub

        while (in.readableBytes() > 4) {

            byte [] magic = new byte[TbaMagic.MAGIC_OFFSET];
            for (int i = 0; i < TbaMagic.MAGIC_OFFSET; i++) {
                magic[i] = in.readByte();
            }
            TbaMagic magicHead = TbaHeadUtil.preParser(magic);

            int msg_len = magicHead.getLength();
            short flag = magicHead.getFlag();

            TbaRpcByteBuffer msg = null;
            byte[] relay = null;
            TbaRpcHead header = null;

            if(flag == TbaEncryptType.WHOLE) {

                byte[] encrypt = new byte[msg_len - 4];
                for (int i = 0; i < msg_len - 4; i++) {
                    encrypt[i] = in.readByte();
                }

                SessionFactory factory = ApplicationContextUtils.getBean(SessionFactory.class);
                Session session = factory.getSessionByChannelId(ctx.channel().id().asLongText());
                String serverRandom = String.valueOf(session.getServerRandom());
                log.info("decrypt key: {}", serverRandom);
                String original = TbaAesUtils.decode(new String(encrypt, "utf-8"), serverRandom);
                relay = original.getBytes("ISO8859-1");
                msg = new TbaRpcByteBuffer(relay, relay.length);
            }
            if(flag == TbaEncryptType.BODY) {

                byte[] all = new byte[TbaHeadUtil.HEAD_SIZE];
                System.arraycopy(magic, 0 , all, 0, TbaMagic.MAGIC_OFFSET);
                for (int i = TbaMagic.MAGIC_OFFSET; i < TbaHeadUtil.HEAD_SIZE; i++) {
                    all[i] = in.readByte();
                }

                byte[] encryptData = new byte[msg_len - TbaHeadUtil.HEAD_SIZE];
                for (int i = 0; i < msg_len - TbaHeadUtil.HEAD_SIZE; i++) {
                    encryptData[i] = in.readByte();
                }

                header = TbaHeadUtil.parser(all);

                if (header.getType() == RpcEventType.ROOMGATE_CONNECT_RES) {

                    SessionFactory factory = ApplicationContextUtils.getBean(SessionFactory.class);
                    Session session = factory.getSessionByChannelId(ctx.channel().id().asLongText());
                    String serverRandom = String.valueOf(session.getServerRandom());
                    log.info("decrypt key: {}", serverRandom);
                    String original = TbaAesUtils.decode(new String(encryptData, "utf-8"), serverRandom);

                    CommonRes res = new TbaSerializeUtils<CommonRes>().deserialize(original.getBytes("ISO8859-1"), CommonRes.class);
                    out.add(res);

                }
                else if (header.getType() == RpcEventType.ROOMGATE_CHAT_RELAY) {

                    SessionFactory factory = ApplicationContextUtils.getBean(SessionFactory.class);
                    Session session = factory.getSessionByChannelId(ctx.channel().id().asLongText());
                    RelayProtocol proxy = new RelayProtocol();
                    proxy.setHead(header);
                    proxy.setData(encryptData);
                    out.add(proxy);

                }

                return;
            }
            else {
                msg = new TbaRpcByteBuffer(msg_len);
                msg.writeI32(msg_len);
                msg.writeI16(flag);
                for (int i = 0; i < msg_len - 6; i++) {
                    msg.writeByte(in.readByte());
                }
            }

            TbaRpcEventParser parser = new TbaRpcEventParser(msg);
            header = parser.Head();

            log.info("msg receive type: {}", header.getType());

            try {
                if (header.getType() == RpcEventType.CONNECT_RES) {
                    TbaRpcProtocolFactory<CommonRes> protocol = new TbaRpcProtocolFactory<CommonRes>(msg);
                    out.add(protocol.Decode(CommonRes.class));
                }
                else if (header.getType() == RpcEventType.MT_HELLO_NOTIFY) {
                    TbaRpcProtocolFactory<HelloNotify> protocol = new TbaRpcProtocolFactory<HelloNotify>(msg);
                    out.add(protocol.Decode(HelloNotify.class));
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
