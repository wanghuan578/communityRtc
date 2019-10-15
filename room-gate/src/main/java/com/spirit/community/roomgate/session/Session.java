package com.spirit.community.roomgate.session;

import com.spirit.community.common.constant.ClientState;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

@Data
public class Session {

    private String channelId;
    private Long connectTime;
    private Long serverRandom;
    private int state;
    private Channel channel = null;
    private Long lastCommunicateTimeStamp = 0l;
    private Long uid;
    private String roomgateId;
    private boolean relayChannel = false;

    public boolean isRelayChannel() {
        return relayChannel;
    }

    public void setRelayChannel(boolean relayChannel) {
        this.relayChannel = relayChannel;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Session(ChannelHandlerContext ctx, Long serverRnd) {
        channelId = ctx.channel().id().asLongText();
        connectTime = System.currentTimeMillis();
        serverRandom = serverRnd;
        state = ClientState.CONNECT_UNAUTHORIZED;
        channel = ctx.channel();
    }

    public Session(ChannelHandlerContext ctx, Long serverRnd, String roomgateId) {
        channelId = ctx.channel().id().asLongText();
        connectTime = System.currentTimeMillis();
        serverRandom = serverRnd;
        state = ClientState.CONNECT_UNAUTHORIZED;
        channel = ctx.channel();
        this.roomgateId = roomgateId;
    }


}
