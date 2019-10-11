package com.spirit.community.roomgate.biz;


import com.spirit.community.common.constant.RpcEventType;
import com.spirit.community.roomgate.context.ApplicationContextUtils;
import com.spirit.community.roomgate.relay.RelayProxy;
import com.spirit.community.roomgate.session.Session;
import com.spirit.community.roomgate.session.SessionFactory;
import com.spirit.tba.Exception.TbaException;
import com.spirit.tba.client.TbaRelayEvent;
import com.spirit.tba.core.*;
import com.spirit.tba.tools.TbaHeadUtil;
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
			if (ev.getEncryptType() == EncryptType.WHOLE) {
				TsRpcHead head = ev.getHead();
				TsRpcProtocolFactory protocol = new TsRpcProtocolFactory<TBase>((TBase)ev.getBody(), head, ev.getLength());
				byte[] buf = protocol.Encode().OutStream().GetBytes();

				SessionFactory factory = ApplicationContextUtils.getBean(SessionFactory.class);
				Session session = factory.getSessionByChannelId(ctx.channel().id().asLongText());
				String key = String.valueOf(session.getServerRandom());
				log.info("encrypt key: {}", key);
				String encrypt = TbaAes.encode(new String(buf, "ISO8859-1"), key);

				TsRpcByteBuffer byteBuff = new TsRpcByteBuffer(encrypt.length() + TsMagic.MAGIC_OFFSET);
				byteBuff.WriteI32(encrypt.length() + TsMagic.MAGIC_OFFSET);
				byteBuff.WriteI16(EncryptType.WHOLE);
				byteBuff.copy(encrypt.getBytes());
				byte [] o = byteBuff.GetBytes();
				out.writeBytes(o, 0, o.length);
			}
			else if (ev.getEncryptType() == EncryptType.BODY) {
				if (ev.getHead().GetType() == RpcEventType.ROOMGATE_CHAT_NOTIFY) {
					RelayProxy proxy = (RelayProxy) ev.getBody();
					int len = proxy.data.length + TbaHeadUtil.HEAD_SIZE;
					TsRpcByteBuffer byteBuff = new TsRpcByteBuffer(len);
					TbaHeadUtil.build(byteBuff, proxy.head, len);
					byteBuff.copy(proxy.data);
					byte [] o = byteBuff.GetBytes();
					out.writeBytes(o, 0, o.length);
				}
			}
			else {
				TsRpcHead head = ev.getHead();
				TsRpcProtocolFactory protocol = new TsRpcProtocolFactory<TBase>((TBase)ev.getBody(), head, ev.getLength());
				byte[] buf = protocol.Encode().OutStream().GetBytes();
				out.writeBytes(buf, 0, buf.length);
			}
		}
		catch (TbaException e) {
			log.error(e.getLocalizedMessage(), e);
		}
	}

}
