package com.spirit.community.login.biz;

import com.spirit.community.common.utils.context.ApplicationContextUtils;
import com.spirit.community.login.session.Session;
import com.spirit.community.login.session.SessionFactory;
import com.spirit.tba.Exception.TbaException;
import com.spirit.tba.core.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TBase;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TbaProtocolEncoder extends MessageToByteEncoder<Object> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

		TbaEvent ev = (TbaEvent) msg;

		try {
			if (ev.isEncrypt()) {
				TsRpcHead head = ev.getHead();
				TsRpcProtocolFactory protocol = new TsRpcProtocolFactory<TBase>((TBase)ev.getBody(), head, ev.getLength());
				byte[] buf = protocol.Encode().OutStream().GetBytes();

				SessionFactory factory = ApplicationContextUtils.getBean(SessionFactory.class);
				Session session = factory.getSessionById(ctx.channel().id().asLongText());
				String key = String.valueOf(session.getServerRandom());
				log.info("encrypt key: {}", key);
				String encrypt = TbaAes.encode(new String(buf, "ISO8859-1"), key);

				TsRpcByteBuffer byteBuff = new TsRpcByteBuffer(encrypt.length() + 6);

				byteBuff.WriteI32(encrypt.length() + 4);
				byteBuff.WriteI16((short)1);
				byteBuff.copy(encrypt.getBytes());
				byte [] o = byteBuff.GetBytes();
				log.info("encrypt out buff len: {}", o.length);
				out.writeBytes(o, 0, o.length);
			}
			else {
				TsRpcHead head = ev.getHead();
				TsRpcProtocolFactory protocol = new TsRpcProtocolFactory<TBase>((TBase)ev.getBody(), head, ev.getLength());
				byte[] buf = protocol.Encode().OutStream().GetBytes();
				log.info("out buff len: {}", buf.length);
				out.writeBytes(buf, 0, buf.length);
			}
		}
		catch (TbaException e) {
			log.error(e.getLocalizedMessage(), e);
		}
	}

}
