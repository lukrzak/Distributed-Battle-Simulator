package com.dbs.dbs.configs;

import com.dbs.dbs.controllers.GameController;
import com.dbs.dbs.enumerations.CommandEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GameWebSocketHandler extends TextWebSocketHandler {
    private final List<WebSocketSession> sessions = new ArrayList<>();
    @Autowired
    private final GameController gameController;

    public GameWebSocketHandler(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessions.add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JsonNode jsonNode = new ObjectMapper().readTree(message.getPayload());
        CommandEnum command = CommandEnum.valueOf(jsonNode.get("command").asText());
        String body = jsonNode.get("body").toString();

        handleCommand(command, body);
        sendMessage(message.getPayload());
    }

    public void sendMessage(String response) throws IOException {
        for (WebSocketSession s : sessions){
            if (!s.isOpen()) continue;
            s.sendMessage(new TextMessage(response));
        }
    }

    private void handleCommand(CommandEnum command, String body) throws JsonProcessingException {
        switch(command){
            case MOVE -> {
                MoveMessage message = new ObjectMapper().readValue(body, MoveMessage.class);
                gameController.moveUnit(message.id, message.posX, message.posY);
            }
            case ATTACK -> {
                AttackMessage message = new ObjectMapper().readValue(body, AttackMessage.class);
                gameController.attackUnit(message.attackerId, message.defenderId);
            }
            case CREATE -> {
            }
        }
    }


    public record MoveMessage(Long id, double posX, double posY){}
    public record AttackMessage(Long attackerId, Long defenderId){}
}
