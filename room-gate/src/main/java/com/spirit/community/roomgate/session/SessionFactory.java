package com.spirit.community.roomgate.session;

import com.alibaba.fastjson.JSON;
import com.spirit.community.common.constant.ClientState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class SessionFactory {

    protected final Map<String, Session> sessionMap;
    //protected final Map<String, RoomGateSession> roomgateSessionMap;

    public SessionFactory() {
        sessionMap = new ConcurrentHashMap<>();
        //roomgateSessionMap = new ConcurrentHashMap<>();
    }

    public void addSession(Session session) {
        log.info("add session: {}", JSON.toJSONString(session, true));
        sessionMap.put(session.getChannelId(), session);
    }

//    public void addRoomGateSession(Session session) {
//        log.info("add roomGateSession: {}", JSON.toJSONString(session, true));
//        sessionMap.put(session.getRoomgateId(), session);
//    }

    public Session getSessionByChannelId(String channelId) {
        return sessionMap.get(channelId);
    }

    public Session getByRoomgateId(String roomgateId) {
        for (Session session : sessionMap.values()) {
            if ((session.getRoomgateId() != null) && session.getRoomgateId().equalsIgnoreCase(roomgateId)) {
                return session;
            }
        }
        return null;
    }

    public Session getRoomGateSessionByChannelId(String channelId) {
        for (Session session : sessionMap.values()) {
            if (session.getChannelId().equalsIgnoreCase(channelId)) {
                return session;
            }
        }
        return null;
    }

    public Session getSessionByUid(Long uid) {//todo data syncrinize
        for (Session sess : sessionMap.values()) {
            if ((sess.getUid() != null) && sess.getUid().longValue() == uid.longValue()) {
                return sess;
            }
        }
        return null;
    }

    public void update(Session session) {
        log.info("update session: {}", JSON.toJSONString(session, true));
        sessionMap.put(session.getChannelId(), session);
    }

    public Session removeById(String channelId) {
        log.info("session channel: {} lost", channelId);
        return sessionMap.remove(channelId);
    }

    public Session removeByRoomgateId(String roomgateId) {
        for (Session sess : sessionMap.values()) {
            if (sess.getRoomgateId().equalsIgnoreCase(roomgateId)) {
                return sessionMap.remove(sess.getChannelId());
            }
        }
        return null;
    }

    public Session getByRoomgateId(String roomgateId) {
        for (Session session : sessionMap.values()) {
            if ((session.getRoomgateId() != null) && session.getRoomgateId().equalsIgnoreCase(roomgateId)) {
                return session;
            }
        }
        return null;
    }

    public void authorized(String channelId, Long uid) {
        if (sessionMap.containsKey(channelId)) {
            Session sess = sessionMap.get(channelId);
            sess.setState(ClientState.CONNECT_AUTHORIZED);
            sess.setUid(uid);
            sessionMap.put(channelId, sess);
        }
    }
}



