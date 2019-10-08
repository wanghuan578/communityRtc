package com.spirit.community.roomgate.session;

import com.spirit.community.common.constant.ClientState;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

@Data
public class Session {

    private String id;
    private Long connectTime;
    private Long serverRandom;
    private int state;
    private Channel channel = null;
    private Long lastCommunicateTimeStamp = 0l;

    public Session(String id) {
        this.id = id;
    }

    public Session(ChannelHandlerContext ctx, Long serverRnd) {
        id = ctx.channel().id().asLongText();
        connectTime = System.currentTimeMillis();
        serverRandom = serverRnd;
        state = ClientState.CONNECT_UNAUTHORIZED;
        channel = ctx.channel();
    }

}
