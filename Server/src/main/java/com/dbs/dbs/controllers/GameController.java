package com.dbs.dbs.controllers;

import com.dbs.dbs.enumerations.UnitEnum;
import com.dbs.dbs.exceptions.TooManyConnectionsException;
import com.dbs.dbs.models.Player;
import com.dbs.dbs.models.units.Unit;
import com.dbs.dbs.services.GameService;
import org.springframework.stereotype.Controller;

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
     * moveUnit creates thread, where unit is moved towards point (posX, posY). It sets unit's moveTask of Thread
     * type to this thread or stops currently running thread that is responsible for moving.
     *
     * @param unitId ID of unit, that will be moved.
     * @param posX   X coordinate of destination.
     * @param posY   Y coordinate of destination.
     */
    public void moveUnit(Long unitId, double posX, double posY) {
        Unit unit = gameService.getUnitOfGivenId(unitId);
        Runnable moveTask = () -> {
            try {
                gameService.moveUnit(unit, posX, posY);
            } catch (InterruptedException e) {
                System.out.println("Direction change");
            }
        };

        if (unit.getMoveTask() != null)
            unit.getMoveTask().interrupt();
        Thread moveThread = new Thread(moveTask);
        unit.setMoveTask(moveThread);
        moveThread.start();
    }

    /**
     * attackUnit is responsible for attacking unit in time intervals. It sets unit's attackTask of Thread type to this
     * thread or stops currently running thread that is responsible for attacking. Thread ends when defender is out of
     * range.
     *
     * @param attackerId ID of unit. This unit will deal damage toward defender.
     * @param defenderId ID of unit. This unit will receive dealt damage.
     */
    public void attackUnit(Long attackerId, Long defenderId) {
        Unit attackingUnit = gameService.getUnitOfGivenId(attackerId);
        Runnable attackTask = () -> {
            try {
                gameService.attackUnit(attackerId, defenderId);
            } catch (Exception e) {
                System.out.println("Direction change");
            }
        };

        if (attackingUnit.getAttackTask() != null)
            attackingUnit.getAttackTask().interrupt();
        Thread attackThread = new Thread(attackTask);
        attackingUnit.setAttackTask(attackThread);
        attackThread.start();
    }

    /**
     * Method creates new unit of given type at given coordinates for player after 2500ms.
     *
     * @param type   Type of unit to create.
     * @param posX   X coordinate of unit creation position.
     * @param posY   Y coordinate of unit creation position.
     * @param player Boolean type - true: playerA, false: playerB. In future player will be passed as byte variable.
     */
    public void createUnit(UnitEnum type, double posX, double posY, Player player) {
        Thread createUnitThread = new Thread(() -> {
            try {
                gameService.createUnit(type, posX, posY, player);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        createUnitThread.start();
    }

    public void initializeNewPlayer() {
        try {
            gameService.initializeNewPlayer();
        } catch (TooManyConnectionsException e) {
            System.out.println("Too many connections");
        }
    }
}
