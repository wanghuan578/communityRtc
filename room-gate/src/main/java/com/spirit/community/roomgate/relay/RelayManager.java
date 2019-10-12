package com.spirit.community.roomgate.relay;

import com.spirit.community.common.pojo.RoomgateUser;
import com.spirit.community.roomgate.redis.RedisUtil;
import com.spirit.community.roomgate.service.RoomGateInfoService;
import com.spirit.community.roomgate.session.Session;
import com.spirit.community.roomgate.session.SessionFactory;
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


    private final Map<String, RelayClient<RelayProxy>> roomgateSession;

    public RelayManager() {
        roomgateSession = new ConcurrentHashMap<>();
    }

    public Map<String, RelayClient<RelayProxy>> getRoomgateSession() {
        return roomgateSession;
    }

    public void putData(String roomgateId, RelayProxy obj) {
        RelayClient<RelayProxy> client = roomgateSession.get(roomgateId);
        if (client != null) {
            client.getRelayMsgQueue().offer(obj);
        }
    }

    public synchronized void openRoomGate(String ip, int port, String roomgateId) {

        RelayClient<RelayProxy> c = new RelayClient<RelayProxy>();
        c.config(new RelayDecoder(roomgateId), new RelayEncoder(roomgateId), new RelayEventHandler(roomgateId));
        try {
            c.connect(ip, port);
            roomgateSession.put(roomgateId, c);
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
