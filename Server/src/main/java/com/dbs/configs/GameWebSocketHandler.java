package com.dbs.configs;

import com.dbs.controllers.GameController;
import com.dbs.enumerations.CommandType;
import com.dbs.enumerations.UnitType;
import com.dbs.models.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class is responsible for handling incoming web socket messages. This handler is defined in WebSocketConfig class to
 * invoke methods depending on given command in incoming message. After successful connection, session is added to
 * session list, that will be used to address all outgoing messages.
 */
public class GameWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new ArrayList<>();
    private final GameController gameController;

    public GameWebSocketHandler(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Function adds user's session. It is automatically invoked after connection between client and server is
     * established.
     *
     * @param session See WebSocketSession documentation.
     * @throws Exception Throws Exception after connection error.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("Connected");
        //gameController.initializeNewPlayer();
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("Connection closed");
        sessions.remove(session);
    }

    /**
     * Method reads incoming command and body of necessary parameters. Message should be passed with JSON standard
     * with elements "command", "player" and "body", that will be parsed into command to handle application logic, player
     * who makes move and body with all needed parameters, respectively.
     *
     * @param session See WebSocketSession documentation.
     * @param message Text message that will be received as TextMessage type.
     * @throws IOException Exception is thrown when error occurs while trying to parse JSON passed as String into object.
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        JsonNode jsonNode = new ObjectMapper().readTree(message.getPayload());
        CommandType command = CommandType.valueOf(jsonNode.get("command").asText());
        String body = jsonNode.get("body").toString();

        handleCommand(command, body);
        sendMessage(message.getPayload());
    }

    /**
     * Method sends message to all connected players.
     *
     * @param response String message that will be sent to connected players.
     * @throws IOException Exception is thrown when error occurs while trying to send message.
     */
    public void sendMessage(String response) throws IOException {
        for (WebSocketSession s : sessions) {
            if (!s.isOpen()) continue;
            s.sendMessage(new TextMessage(response));
        }
    }

    /**
     * Method that calls GameController methods, based on received command of CommandEnum type.
     *
     * @param command Logic to handle.
     * @param body    Json given in String with body that contains all necessary parameters to pass.
     * @throws JsonProcessingException Exception is thrown when error occurs while trying to parse JSON passed as String into object.
     */
    private void handleCommand(CommandType command, String body) throws JsonProcessingException {
        switch (command) {
            case MOVE -> {
                MoveMessage message = new ObjectMapper().readValue(body, MoveMessage.class);
                gameController.moveUnit(message.id, message.posX, message.posY, message.gameId);
            }
            case ATTACK -> {
                AttackMessage message = new ObjectMapper().readValue(body, AttackMessage.class);
                gameController.attackUnit(message.attackerId, message.defenderId, message.gameId);
            }
            case CREATE -> {
                CreateMessage message = new ObjectMapper().readValue(body, CreateMessage.class);
                gameController.createUnit(message.type, message.posX, message.posY, message.player);
            }
        }
    }

    /**
     * Record that will contain all information about unit to move.
     *
     * @param id   Unit ID.
     * @param posX Desired X coordinate to move unit toward.
     * @param posY Desired Y coordinate to move unit toward.
     */
    public record MoveMessage(Long id, double posX, double posY, String gameId) {
    }

    /**
     * Record that will contain all information about units to fight.
     *
     * @param attackerId ID of attacker Unit.
     * @param defenderId ID of defender Unit.
     */
    public record AttackMessage(Long attackerId, Long defenderId, String gameId) {
    }

    public record CreateMessage(UnitType type, double posX, double posY, Player player, String gameId) {
    }
}
