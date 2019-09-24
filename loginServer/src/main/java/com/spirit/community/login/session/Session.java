package com.spirit.community.login.session;

import io.netty.channel.ChannelHandlerContext;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Session {

    protected final Map<ChannelHandlerContext, ServiceStatus> providerSessionMap;
    protected final Map<ChannelHandlerContext, ServiceStatus> consumerSessionMap;
    protected final Map<String, List<ChannelHandlerContext>> pathChannelHandlerContextListRelationship;

    public Session() {
        providerSessionMap = new ConcurrentHashMap<>();
        consumerSessionMap = new ConcurrentHashMap<>();
        pathChannelHandlerContextListRelationship = new ConcurrentHashMap<>();
    }
}
