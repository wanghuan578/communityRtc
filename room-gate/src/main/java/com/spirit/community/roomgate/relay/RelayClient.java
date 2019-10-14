package com.spirit.community.roomgate.relay;

import com.spirit.tba.core.EncryptType;
import com.spirit.tba.core.TbaEvent;
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
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class RelayClient<T extends Proxy> {

    private Channel channel = null;
    private ByteToMessageDecoder decoder = null;
    private MessageToByteEncoder<Object> encoder = null;
    private SimpleChannelInboundHandler eventHandler = null;
    private final Queue<T> relayMsgQueue;
    private volatile boolean running;
    private boolean auth;
    private Object lock = new Object();

    public RelayClient() {
        relayMsgQueue = new LinkedBlockingQueue<T>();
        running = true;
        auth = false;
    }

    public Queue<T> getRelayMsgQueue() {
        return relayMsgQueue;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
        synchronized (lock) {
            lock.notify();
        }
    }

    public void relay() {

        new Thread(new Runnable() {

            @Override
            public void run() {

                log.info("relay running...");

                while (running) {
                    synchronized (lock) {
                        if (!auth) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                log.error(e.getLocalizedMessage(), e);
                            }
                        }
                    }
                    log.info("roomgate relay connect successfully!");
                    T proxy = relayMsgQueue.poll();
                    if (proxy != null) {
                        channel.writeAndFlush(new TbaEvent(proxy.getHead(), proxy, 512, EncryptType.BODY));
                    }
                    else {
                        log.info("empty msg queue");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            log.error(e.getLocalizedMessage(), e);
                        }
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
        relayMsgQueue.offer(ev);
    }


}
