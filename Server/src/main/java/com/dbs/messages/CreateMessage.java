package com.dbs.messages;

import com.dbs.enumerations.UnitType;
import com.dbs.models.Player;

public record CreateMessage(
        UnitType type,
        double posX,
        double posY,
        Player player,
        String gameId) {
}