package com.dbs.dbs.controllers;

import com.dbs.dbs.enumerations.UnitEnum;
import com.dbs.dbs.models.Game;
import com.dbs.dbs.models.units.Unit;
import com.dbs.dbs.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * GameController is WebSocket controller, that receives and manages incoming messages.
 */
@Controller
public class GameController{

    /**
     * Instance of GameService.
     */
    private final GameService gameService;

    /**
     * Constructor of GameController.
     *
     * @param gameService Autowired GameService instance.
     */
    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * moveUnit creates thread, where unit is moved towards point (posX, posY).
     * @param unitId ID of unit, that will be moved.
     * @param posX X coordinate of destination.
     * @param posY Y coordinate of destination.
     */
    public void moveUnit(Long unitId, double posX, double posY){
        Unit unit = gameService.getUnitOfGivenId(unitId);
        Runnable moveTask = () -> {
            try {
                gameService.moveUnit(unit, posX, posY);
            } catch (InterruptedException e) {
                System.out.println("sleep interrupt");
            }
        };

        if(unit.getMoveTask() != null) unit.getMoveTask().interrupt();
        Thread moveThread = new Thread(moveTask);
        unit.setMoveTask(moveThread);
        moveThread.start();
    }

    /**
     * attackUnit is responsible for attacking unit in time intervals.
     * Method creates new Thread, that ends when defender is out of range.
     * @param attackerId ID of unit. This unit will deal damage toward defender.
     * @param defenderId ID of unit. This unit will receive dealt damage.
     */
    public void attackUnit(Long attackerId, Long defenderId){
        Unit attackingUnit = gameService.getUnitOfGivenId(attackerId);
        Runnable attackTask = () -> {
            try {
                gameService.attackUnit(attackerId, defenderId);
            } catch (Exception e) {
                System.out.println("sleep interrupt");
            }
        };

        if(attackingUnit.getAttackTask() != null) attackingUnit.getAttackTask().interrupt();
        Thread attackThread = new Thread(attackTask);
        attackingUnit.setAttackTask(attackThread);
        attackThread.start();
    }

    public void createUnit(UnitEnum type, double posX, double posY, boolean player){
        Thread createUnitThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    gameService.createUnit(type, posX, posY, player);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        createUnitThread.start();
    }
}
