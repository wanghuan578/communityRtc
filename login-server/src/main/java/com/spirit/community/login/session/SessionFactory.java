package com.spirit.community.login.session;

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
        sessionMap.put(session.getId(), session);
    }

    public Session getSessionById(String channelId) {
        return sessionMap.get(channelId);
    }

    public void update(Session session) {
        log.info("update session: {}", JSON.toJSONString(session, true));
        sessionMap.put(session.getId(), session);
    }

    public Session removeById(String channelId) {
        return sessionMap.remove(channelId);
    }

    public void authorized(String id) {
        if (sessionMap.containsKey(id)) {
            Session sess = sessionMap.get(id);
            sess.setState(ClientState.CONNECT_AUTHORIZED);
            sessionMap.put(id, sess);
        }
    }
}



