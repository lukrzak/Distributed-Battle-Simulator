package com.dbs.messages;

public record AttackMessage(
        Long attackerId,
        Long defenderId,
        String gameId) {
}
