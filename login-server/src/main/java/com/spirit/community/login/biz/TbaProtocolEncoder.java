package com.spirit.community.login.biz;


import com.spirit.community.login.context.ApplicationContextUtils;
import com.spirit.community.login.session.Session;
import com.spirit.community.login.session.SessionFactory;
import com.spirit.tba.exception.TbaException;
import com.spirit.tba.core.*;
import com.spirit.tba.tools.TbaAesUtils;
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
			if (ev.getEncryptType() == TbaEncryptType.WHOLE) {
				TbaRpcHead head = ev.getHead();
				TbaRpcProtocolFactory protocol = new TbaRpcProtocolFactory<TBase>((TBase)ev.getBody(), head, ev.getLength());
				byte[] buf = protocol.Encode().OutStream().toBytes();

				SessionFactory factory = ApplicationContextUtils.getBean(SessionFactory.class);
				Session session = factory.getSessionById(ctx.channel().id().asLongText());
				String key = String.valueOf(session.getServerRandom());
				log.info("encrypt key: {}", key);
				String encrypt = TbaAesUtils.encode(new String(buf, "ISO8859-1"), key);

				TbaRpcByteBuffer byteBuff = new TbaRpcByteBuffer(encrypt.length() + TbaMagic.MAGIC_OFFSET);

				byteBuff.writeI32(encrypt.length() + TbaMagic.MAGIC_OFFSET);
				byteBuff.writeI16(ev.getEncryptType());
				byteBuff.append(encrypt.getBytes());
				byte [] o = byteBuff.toBytes();
				log.info("encrypt out buff len: {}", o.length);
				out.writeBytes(o, 0, o.length);
			}
			else {
				TbaRpcHead head = ev.getHead();
				TbaRpcProtocolFactory protocol = new TbaRpcProtocolFactory<TBase>((TBase)ev.getBody(), head, ev.getLength());
				byte[] buf = protocol.Encode().OutStream().toBytes();
				log.info("out buff len: {}", buf.length);
				out.writeBytes(buf, 0, buf.length);
			}
		}
		catch (TbaException e) {
			log.error(e.getLocalizedMessage(), e);
		}
	}

}
