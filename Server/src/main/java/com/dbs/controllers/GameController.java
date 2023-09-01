package com.dbs.controllers;

import com.dbs.enumerations.UnitType;
import com.dbs.models.Game;
import com.dbs.models.Player;
import com.dbs.models.units.Unit;
import com.dbs.services.GameService;
import org.springframework.stereotype.Controller;

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
        Game game = gameService.findGameById(gameId)
                .orElseThrow(() -> new NullPointerException("Game with given id doesn't exist"));
        Unit unit = gameService.getUnitOfGivenId(unitId, game)
                .orElseThrow(() -> new NullPointerException("Unit with id " + unitId + " doesn't exist"));

        gameService.moveUnit(unit, posX, posY);
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
        Game game = gameService.findGameById(gameId)
                .orElseThrow(() -> new NullPointerException("Game with given id doesn't exist"));
        Unit attacker = gameService.getUnitOfGivenId(attackerId, game)
                .orElseThrow(() -> new NullPointerException("Unit with id " + attackerId + " doesn't exist"));
        Unit defender = gameService.getUnitOfGivenId(defenderId, game)
                .orElseThrow(() -> new NullPointerException("Unit with id " + defenderId + " doesn't exist"));

        gameService.attackUnit(attacker, defender);
    }

    /**
     * Method creates new unit of given type at given coordinates for player.
     *
     * @param type   Type of unit to create.
     * @param posX   X coordinate of unit creation position.
     * @param posY   Y coordinate of unit creation position.
     * @param player Player who's creating unit
     */
    public void createUnit(UnitType type, double posX, double posY, Player player) {
        gameService.createUnit(type, posX, posY, player);
    }
}
