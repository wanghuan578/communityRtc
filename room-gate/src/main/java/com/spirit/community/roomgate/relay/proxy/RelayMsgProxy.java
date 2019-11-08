package com.spirit.community.roomgate.relay.proxy;

import com.spirit.community.common.constant.RpcEventType;
import com.spirit.community.roomgate.relay.session.Protocol;
import com.spirit.tba.core.TbaEncryptType;
import com.spirit.tba.core.TbaEvent;
import com.spirit.tba.core.TbaRpcHead;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import java.net.InetSocketAddress;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class RelayMsgProxy<T extends Protocol> {

    private Channel channel = null;
    private ByteToMessageDecoder decoder = null;
    private MessageToByteEncoder<Object> encoder = null;
    private SimpleChannelInboundHandler eventHandler = null;
    private final BlockingQueue<T> bQueue;
    private volatile boolean running;
    private boolean auth;
    private Object ready = new Object();

    public RelayMsgProxy() {
        bQueue = new LinkedBlockingQueue<T>(65535);
        running = true;
        auth = false;
    }

    public Queue<T> getRelayMsgQueue() {
        return bQueue;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
        synchronized (ready) {
            ready.notify();
        }
    }

    public void relay() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    synchronized (ready) {
                        if (!auth) {
                            try {
                                ready.wait();
                                log.info("roomgate relay proxy ready!");
                            } catch (InterruptedException e) {
                                log.error(e.getLocalizedMessage(), e);
                            }
                        }
                    }

                    try {
                        T elem = bQueue.take();
                        log.info("relay msg send...");
                        TbaRpcHead head = new TbaRpcHead();
                        head.setType((short) RpcEventType.ROOMGATE_CHAT_RELAY);
                        channel.writeAndFlush(new TbaEvent(head, elem, 512, TbaEncryptType.BODY));
                    } catch (InterruptedException e) {
                        log.error(e.getLocalizedMessage(), e);
                    }
                }
            }
        }).start();
    }

    public void config(ByteToMessageDecoder decoder, MessageToByteEncoder<Object> encoder, SimpleChannelInboundHandler eventHandler) {
        this.decoder = decoder;
        this.encoder = encoder;
        this.eventHandler = eventHandler;
    }

    public void connect(String host, Integer port) throws Exception {

        NioEventLoopGroup group = new NioEventLoopGroup();

        new Bootstrap().channel(NioSocketChannel.class)
                .group(group)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new IdleStateHandler(0, 30, 0));
                        pipeline.addLast("decode", decoder);
                        pipeline.addLast("encode", encoder);
                        pipeline.addLast(eventHandler);
                    }
                })
                .connect(new InetSocketAddress(host, port))
                .addListener((ChannelFutureListener) future -> {
                    if (future.isSuccess()) {
                        channel = (SocketChannel) future.channel();
                    } else {
                        future.channel().close();
                        group.shutdownGracefully();
                    }
                });
    }

    public void close() {
        channel.close();
    }

    public Channel getChannel() {
        return channel;
    }

    public void putRelayEvent(T ev) {
        bQueue.offer(ev);
    }


}
