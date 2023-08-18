package com.dbs.controllers;

import com.dbs.enumerations.UnitType;
import com.dbs.models.Player;
import com.dbs.models.units.Unit;
import com.dbs.services.GameService;
import org.springframework.stereotype.Controller;

import java.util.Optional;

/**
 * GameController is WebSocket controller, that receives and manages incoming messages.
 */
@Controller
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * moveUnit invokes method in gameService, that creates thread, where unit is moved towards point (posX, posY).
     * It sets unit's moveTask of Thread type to this thread or stops currently running thread that is responsible for moving.
     *
     * @param unitId ID of unit, that will be moved.
     * @param posX   X coordinate of destination.
     * @param posY   Y coordinate of destination.
     */
    public void moveUnit(Long unitId, double posX, double posY, String gameId) {
        Optional<Unit> unit = gameService.getUnitOfGivenId(unitId, gameId);
        if (unit.isEmpty())
            throw new NullPointerException("Unit with id " + unitId + " doesn't exist");

        gameService.moveUnit(unit.get(), posX, posY);
    }

    /**
     * attackUnit is responsible for attacking unit in time intervals. It sets unit's attackTask of Thread type to this
     * thread or stops currently running thread that is responsible for attacking. Thread ends when defender is out of
     * range.
     *
     * @param attackerId ID of unit. This unit will deal damage toward defender.
     * @param defenderId ID of unit. This unit will receive dealt damage.
     */
    public void attackUnit(Long attackerId, Long defenderId, String gameId) {
        Optional<Unit> attacker = gameService.getUnitOfGivenId(attackerId, gameId);
        Optional<Unit> defender = gameService.getUnitOfGivenId(attackerId, gameId);
        if (attacker.isEmpty() || defender.isEmpty())
            throw new NullPointerException("Unit with id " + attackerId + " or " + defenderId + " doesn't exist");

        gameService.attackUnit(attacker.get(), defender.get());
    }

    /**
     * Method creates new unit of given type at given coordinates for player after 2500ms.
     *
     * @param type   Type of unit to create.
     * @param posX   X coordinate of unit creation position.
     * @param posY   Y coordinate of unit creation position.
     * @param player Boolean type - true: playerA, false: playerB. In future player will be passed as byte variable.
     */
    public void createUnit(UnitType type, double posX, double posY, Player player) {
        gameService.createUnit(type, posX, posY, player);
    }
}
