package com.dbs.dbs.controllers;

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
        Thread moveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    gameService.moveUnit(unit, posX, posY);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        moveThread.start();
    }

    /**
     * attackUnit is responsible for attacking unit in time intervals.
     * Method creates new Thread, that ends when defender is out of range.
     * @param attackerId ID of unit. This unit will deal damage toward defender.
     * @param defenderId ID of unit. This unit will receive dealt damage.
     */
    public void attackUnit(Long attackerId, Long defenderId){
        Unit attacker = gameService.getUnitOfGivenId(attackerId);
        Unit defender = gameService.getUnitOfGivenId(defenderId);
        Thread attackThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    gameService.attackUnit(attacker, defender);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        attackThread.start();
    }
}
