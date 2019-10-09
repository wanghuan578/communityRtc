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

    public SessionFactory() {
        sessionMap = new ConcurrentHashMap<>();
    }

    public void addSession(Session session) {
        log.info("add session: {}", JSON.toJSONString(session, true));
        sessionMap.put(session.getChannelId(), session);
    }

    public Session getSessionByChannelId(String channelId) {
        return sessionMap.get(channelId);
    }

    public Session getSessionByUid(Long uid) {//todo data syncrinize
        for (Session sess : sessionMap.values()) {
            if (sess.getUid() == uid) {
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
        return sessionMap.remove(channelId);
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



