package com.dbs.dbs.configs;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class GameWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message){
        System.out.println(message.getPayload());
    }
}
