package com.spirit.community.roomServer.netty;


import com.spirit.community.roomServer.biz.ServerEventHandler;
import com.spirit.community.roomServer.biz.TbaProtocolDecoder;
import com.spirit.community.roomServer.biz.TbaProtocolEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Slf4j
@Component
public class NettyServerConfig {

    @Value("${netty.port}")
    private  Integer port;

    @Resource
    private ServerEventHandler serverChannelHandler;

    @Autowired
    private TbaProtocolDecoder tbaProtocolDecoder;

    @Autowired
    private TbaProtocolEncoder tbaProtocolEncoder;

    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workGroup = new NioEventLoopGroup();

    @PostConstruct
    public void start() throws InterruptedException {

        ServerBootstrap b=new ServerBootstrap();

        b.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,128)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast("decode", new TbaProtocolDecoder());
                        socketChannel.pipeline().addLast("encode", new TbaProtocolEncoder());
                        socketChannel.pipeline().addLast(serverChannelHandler);
                    }
                });

        ChannelFuture future = b.bind(port).sync();

        if (future.isSuccess()) {
            log.info("Netty Listen Port: {}", port);
        }
    }

    @PreDestroy
    public void destroy() {
        bossGroup.shutdownGracefully().syncUninterruptibly();
        workGroup.shutdownGracefully().syncUninterruptibly();
        log.info("Netty ShutDown");
    }
}
