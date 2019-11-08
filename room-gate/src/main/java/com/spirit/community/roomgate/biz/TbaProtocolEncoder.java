package com.spirit.community.roomgate.biz;


import com.spirit.community.common.constant.RpcEventType;
import com.spirit.community.roomgate.context.ApplicationContextUtils;
import com.spirit.community.roomgate.relay.session.RelayProtocol;
import com.spirit.community.roomgate.session.Session;
import com.spirit.community.roomgate.session.SessionFactory;
import com.spirit.tba.exception.TbaException;
import com.spirit.tba.client.TbaRelayEvent;
import com.spirit.tba.core.*;
import com.spirit.tba.tools.TbaAesUtils;
import com.spirit.tba.tools.TbaHeadUtil;
import com.spirit.tba.tools.TbaSerializeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TBase;

@Slf4j
public class TbaProtocolEncoder extends MessageToByteEncoder<Object> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

		if (msg instanceof TbaRelayEvent) {
			TbaRelayEvent ev = (TbaRelayEvent) msg;
			byte[] relay = ev.getRelayMsg();
			log.info("relay msg len: {}", relay.length);
			out.writeBytes(relay, 0, relay.length);
			return;
		}

		TbaEvent ev = (TbaEvent) msg;

		try {
			if (ev.getEncryptType() == TbaEncryptType.WHOLE) {
				TbaRpcHead head = ev.getHead();
				TbaRpcProtocolFactory protocol = new TbaRpcProtocolFactory<TBase>((TBase)ev.getBody(), head, ev.getLength());
				byte[] buf = protocol.Encode().OutStream().toBytes();

				SessionFactory factory = ApplicationContextUtils.getBean(SessionFactory.class);
				Session session = factory.getSessionByChannelId(ctx.channel().id().asLongText());
				String key = String.valueOf(session.getServerRandom());
				log.info("encrypt key: {}", key);
				String encrypt = TbaAesUtils.encode(new String(buf, "ISO8859-1"), key);

				TbaRpcByteBuffer byteBuff = new TbaRpcByteBuffer(encrypt.length() + TbaMagic.MAGIC_OFFSET);
				byteBuff.writeI32(encrypt.length() + TbaMagic.MAGIC_OFFSET);
				byteBuff.writeI16(TbaEncryptType.WHOLE);
				byteBuff.append(encrypt.getBytes());
				byte [] o = byteBuff.toBytes();
				out.writeBytes(o, 0, o.length);
			}
			else if (ev.getEncryptType() == TbaEncryptType.BODY) {
				if (ev.getHead().getType() == RpcEventType.ROOMGATE_CHAT_NOTIFY) {
					RelayProtocol proxy = (RelayProtocol) ev.getBody();
					int len = proxy.getData().length + TbaHeadUtil.HEAD_SIZE;
					TbaRpcByteBuffer byteBuff = new TbaRpcByteBuffer(len);
					TbaHeadUtil.build(byteBuff, proxy.getHead(), len);
					byteBuff.append(proxy.getData());
					byte [] o = byteBuff.toBytes();
					out.writeBytes(o, 0, o.length);
				}
				else if (ev.getHead().getType() == RpcEventType.ROOMGATE_CHAT_RELAY) {
					RelayProtocol proxy = (RelayProtocol) ev.getBody();
					proxy.getHead().setFlag(TbaEncryptType.BODY);
					int len = proxy.getData().length + TbaHeadUtil.HEAD_SIZE;
					TbaRpcByteBuffer protocol = new TbaRpcByteBuffer(len);
					TbaHeadUtil.build(protocol, proxy.getHead(), len);
					protocol.append(proxy.getData());
					byte [] o = protocol.toBytes();
					log.info("encrypt msg len: " + o.length);
					out.writeBytes(o, 0, o.length);
				}
				else {
					byte[] data = new TbaSerializeUtils<TBase>().serialize((TBase) ev.getBody(), ev.getLength());
					SessionFactory factory = ApplicationContextUtils.getBean(SessionFactory.class);
					Session roomGateSession = factory.getRoomGateSessionByChannelId(ctx.channel().id().asLongText());
					TbaRpcHead head = ev.getHead();
					head.setFlag(ev.getEncryptType());
					log.info("encrypt key: " + roomGateSession.getServerRandom());
					String encrypt = TbaAesUtils.encode(new String(data, "ISO8859-1"), String.valueOf(roomGateSession.getServerRandom()));
					int len = encrypt.length() + TbaHeadUtil.HEAD_SIZE;
					TbaRpcByteBuffer protocol = new TbaRpcByteBuffer(len);
					TbaHeadUtil.build(protocol, head, len);
					protocol.append(encrypt.getBytes());
					byte [] o = protocol.toBytes();
					log.info("encrypt msg len: " + o.length);
					out.writeBytes(o, 0, o.length);
				}
			}
			else {
				TbaRpcHead head = ev.getHead();
				TbaRpcProtocolFactory protocol = new TbaRpcProtocolFactory<TBase>((TBase)ev.getBody(), head, ev.getLength());
				byte[] buf = protocol.Encode().OutStream().toBytes();
				out.writeBytes(buf, 0, buf.length);
			}
		}
		catch (TbaException e) {
			log.error(e.getLocalizedMessage(), e);
		}
	}

}
