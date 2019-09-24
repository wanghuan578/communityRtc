package com.spirit.community.login.biz;


import com.spirit.tba.Exception.TbaException;
import com.spirit.tba.core.TsEvent;
import com.spirit.tba.core.TsRpcHead;
import com.spirit.tba.core.TsRpcProtocolFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TBase;

@Slf4j
public class TbaProtocolEncoder extends MessageToByteEncoder<Object> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

		TsEvent ev = (TsEvent) msg;

		try {
			TsRpcHead head = ev.getHead();
			TsRpcProtocolFactory protocol = new TsRpcProtocolFactory<TBase>((TBase)ev.getBody(), head, ev.getLen());
			byte[] buf = protocol.Encode().OutStream().GetBytes();
			log.info("encode msg len: {}", buf.length);
			out.writeBytes(buf, 0, buf.length);
		}
		catch (TbaException e) {
			log.error(e.getLocalizedMessage(), e);
		}
	}

}
