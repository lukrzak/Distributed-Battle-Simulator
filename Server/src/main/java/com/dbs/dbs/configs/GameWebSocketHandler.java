package com.dbs.dbs.configs;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GameWebSocketHandler extends TextWebSocketHandler {
    private final List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessions.add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println(message.getPayload());
        sendMessage(message.getPayload());
    }

    public void sendMessage(String response) throws IOException {
        for (WebSocketSession s : sessions){
            if (!s.isOpen()) continue;
            s.sendMessage(new TextMessage(response));
        }
    }
}
