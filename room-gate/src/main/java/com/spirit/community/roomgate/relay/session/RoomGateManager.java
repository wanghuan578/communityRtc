package com.spirit.community.roomgate.relay.session;

import com.spirit.community.common.exception.MainStageException;
import com.spirit.community.common.pojo.RoomgateUser;
import com.spirit.community.roomgate.redis.RedisUtil;
import com.spirit.community.roomgate.relay.proxy.GateRelayMsgProxy;
import com.spirit.community.roomgate.relay.biz.RelayMsgDecoder;
import com.spirit.community.roomgate.relay.biz.RelayMsgEncoder;
import com.spirit.community.roomgate.relay.biz.RelayEventHandler;
import com.spirit.community.roomgate.service.RoomGateInfoService;
import com.spirit.community.roomgate.session.Session;
import com.spirit.community.roomgate.session.SessionFactory;
import com.spirit.tba.client.TbaRelayEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.spirit.community.common.exception.ExceptionCode.ROOMGATE_RELAY_CHANNEL_EXIST;

@Slf4j
@Component
public class RoomGateManager {

    @Autowired
    private RoomGateInfoService roomGateInfoService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SessionFactory sessionFactory;


    private final Map<String, GateRelayMsgProxy<RelayProtocol>> roomgateSession;

    public RoomGateManager() {
        roomgateSession = new ConcurrentHashMap<>();
    }

    public Map<String, GateRelayMsgProxy<RelayProtocol>> getRoomgateSession() {
        return roomgateSession;
    }

    public synchronized void putData(String roomgateId, RelayProtocol obj) {
        GateRelayMsgProxy<RelayProtocol> gate = roomgateSession.get(roomgateId);
        if (gate != null) {
            gate.getRelayMsgQueue().offer(obj);
        }
    }

    public Boolean isConnect(String roomgateId) {
        if (roomgateSession.get(roomgateId) != null) {
            return true;
        }
        return false;
    }

    public synchronized void openRoomGate(String ip, int port, String roomgateId, RelayProtocol ev) throws MainStageException {

        if (roomgateSession.get(roomgateId) != null) {
            throw new MainStageException(ROOMGATE_RELAY_CHANNEL_EXIST);
        }
        GateRelayMsgProxy<RelayProtocol> g = new GateRelayMsgProxy<RelayProtocol>();
        g.config(new RelayMsgDecoder(roomgateId), new RelayMsgEncoder(roomgateId), new RelayEventHandler(roomgateId));
        try {
            g.eventLoop();
            g.connect(ip, port);
            g.putEvent(ev);
            roomgateSession.put(roomgateId, g);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    public void close(String roomgateId) {
        log.info("RelayManager roomgateId: {} close", roomgateId);
        roomgateSession.remove(roomgateId);
    }
    public void relayMessage(Long uid, byte[] msg) {

        RoomgateUser user = (RoomgateUser) redisUtil.get(String.valueOf(uid));
        if (user.getRoomGateInfo().getRoomGateId() == roomGateInfoService.getRoomGateInfo().getRoomGateId()) {
            Session sess = sessionFactory.getSessionByUid(uid);
            sess.getChannel().writeAndFlush(new TbaRelayEvent(msg));
        }
    }

    public GateRelayMsgProxy getRelayClientByRoomgateId(String roomgateId) {
        return roomgateSession.get(roomgateId);
    }
}
