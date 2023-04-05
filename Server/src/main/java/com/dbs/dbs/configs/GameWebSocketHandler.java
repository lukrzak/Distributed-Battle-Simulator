package com.dbs.dbs.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class GameWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws JsonProcessingException {
        System.out.println(message.getPayload());
    }
}
