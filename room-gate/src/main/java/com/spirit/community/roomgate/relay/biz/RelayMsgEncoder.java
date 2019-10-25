package com.spirit.community.roomgate.relay.biz;


import com.spirit.community.common.constant.RpcEventType;
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
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TBase;

@Slf4j
public class RelayMsgEncoder extends MessageToByteEncoder<Object> {

	private String roomGateId;

	public RelayMsgEncoder(String serverId) {
		this.roomGateId = serverId;
	}

	public String getServerId() {
		return roomGateId;
	}

	public void setServerId(String roomGateId) {
		this.roomGateId = roomGateId;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

		TbaEvent ev = (TbaEvent) msg;

		try {
			if (ev.getEncryptType() == EncryptType.WHOLE) {
				TsRpcHead head = ev.getHead();
				TsRpcProtocolFactory protocol = new TsRpcProtocolFactory<TBase>((TBase)ev.getBody(), head, ev.getLength());
				byte[] buf = protocol.Encode().OutStream().toBytes();

				SessionFactory factory = ApplicationContextUtils.getBean(SessionFactory.class);
				Session session = factory.getSessionByChannelId(ctx.channel().id().asLongText());
				String key = String.valueOf(session.getServerRandom());
				log.info("encrypt key: {}", key);
				String encrypt = TbaAes.encode(new String(buf, "ISO8859-1"), key);

				TsRpcByteBuffer byteBuff = new TsRpcByteBuffer(encrypt.length() + 6);

				byteBuff.writeI32(encrypt.length() + 4);
				byteBuff.writeI16((short)1);
				byteBuff.append(encrypt.getBytes());
				byte [] o = byteBuff.toBytes();
				log.info("encrypt out buff len: {}", o.length);
				out.writeBytes(o, 0, o.length);
			}
			else if (ev.getEncryptType() == EncryptType.BODY) {

				TsRpcHead head = ev.getHead();
				head.SetFlag(ev.getEncryptType());

				if (head.GetType() == RpcEventType.ROOMGATE_CHAT_RELAY) {
					RelayProtocol proxy = (RelayProtocol) ev.getBody();
					proxy.getHead().SetFlag(EncryptType.BODY);
					int len = proxy.getData().length + TbaHeadUtil.HEAD_SIZE;
					TsRpcByteBuffer protocol = new TsRpcByteBuffer(len);
					proxy.getHead().SetType((short) RpcEventType.ROOMGATE_CHAT_RELAY);
					TbaHeadUtil.build(protocol, proxy.getHead(), len);
					protocol.append(proxy.getData());
					byte [] o = protocol.toBytes();
					log.info("encrypt msg len: " + o.length);
					out.writeBytes(o, 0, o.length);
				}
				else {
					try {
						byte[] data = new TbaToolsKit<TBase>().serialize((TBase) ev.getBody(), ev.getLength());
						SessionFactory factory = ApplicationContextUtils.getBean(SessionFactory.class);
						Session roomGateSession = factory.getRoomGateSessionByChannelId(ctx.channel().id().asLongText());

						log.info("encrypt key: " + roomGateSession.getServerRandom());
						String encrypt = TbaAes.encode(new String(data, "ISO8859-1"), String.valueOf(roomGateSession.getServerRandom()));
						int len = encrypt.length() + TbaHeadUtil.HEAD_SIZE;
						TsRpcByteBuffer protocol = new TsRpcByteBuffer(len);
						TbaHeadUtil.build(protocol, head, len);
						protocol.append(encrypt.getBytes());
						byte [] o = protocol.toBytes();
						log.info("encrypt msg len: " + o.length);
						out.writeBytes(o, 0, o.length);
					} catch (TbaException e) {
						log.error(e.getLocalizedMessage(), e);
					}
				}

			}
			else {
				TsRpcHead head = ev.getHead();
				TsRpcProtocolFactory protocol = new TsRpcProtocolFactory<TBase>((TBase)ev.getBody(), head, ev.getLength());
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
