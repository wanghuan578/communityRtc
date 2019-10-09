package com.spirit.community.roomgate.relay;

import com.spirit.community.common.pojo.RoomgateUser;
import com.spirit.community.roomgate.context.ApplicationContextUtils;
import com.spirit.community.roomgate.redis.RedisUtil;
import com.spirit.community.roomgate.service.RoomGateInfoService;
import com.spirit.community.roomgate.session.Session;
import com.spirit.community.roomgate.session.SessionFactory;
import com.spirit.tba.client.TbaClient;
import com.spirit.tba.client.TbaRelayEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class RelayManager {

    @Autowired
    private RoomGateInfoService roomGateInfoService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SessionFactory sessionFactory;

    private Map<String, TbaClient<Object>> serverSession;

    public RelayManager() {
        serverSession = new ConcurrentHashMap<>();
    }

    public void openServer(String ip, int port, String serverId) {
        TbaClient<Object> c = new TbaClient<Object>();
        c.config(new RelayDecoder(serverId), new RelayEncoder(serverId), new RelayEventHandler(serverId));
        try {
            c.connect(ip, port);
            serverSession.put(serverId, c);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    public void relayMessage(Long uid, byte[] msg) {

        RoomgateUser user = (RoomgateUser) redisUtil.get(String.valueOf(uid));
        if (user.getRoomGateInfo().getRoomGateId() == roomGateInfoService.getRoomGateInfo().getRoomGateId()) {
            Session sess = sessionFactory.getSessionByUid(uid);
            sess.getChannel().writeAndFlush(new TbaRelayEvent(msg));
        }
    }
}
