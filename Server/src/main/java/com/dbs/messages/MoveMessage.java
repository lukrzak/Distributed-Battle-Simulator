package com.dbs.messages;

public record MoveMessage(
        Long id,
        double posX,
        double posY,
        String gameId) {
}
