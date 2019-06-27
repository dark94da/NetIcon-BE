package com.neticon.neticon.service.impl;

import com.neticon.neticon.service.WebSocketService;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class WebSocketServiceImpl implements WebSocketHandler, WebSocketService {
    private Map<String, WebSocketSession> sessionMap;

    @PostConstruct
    public void init() {
        sessionMap = new HashMap<>();
    }

    @PreDestroy
    public void cleanup() {
        for (WebSocketSession session : sessionMap.values()) {
            try {
                session.close();
            } catch (IOException ignored) {
            }
        }
    }

    @Override
    public void addNewSession() {

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String nickname = (String) session.getAttributes().get("nickname");
        sessionMap.put(nickname, session);
        System.out.println("user coming: " + nickname);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String content = (String) message.getPayload();
        System.out.println(content);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String nickname = (String) session.getAttributes().get("nickname");
        sessionMap.remove(nickname);
        System.out.println("user leaving: " + nickname);

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
