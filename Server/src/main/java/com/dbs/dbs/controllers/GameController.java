package com.dbs.dbs.controllers;

import com.dbs.dbs.models.units.Unit;
import com.dbs.dbs.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
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
     * @param gameService Autowired GameService instance.
     */
    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Method for connection test. After successful invocation, displays current time.
     */
    @MessageMapping("/test")
    public void test(){
        System.out.println(java.time.LocalTime.now());
    }

    /**
     * moveUnit creates thread, where unit is moved towards point (posX, posY).
     * @param unit Object of Unit type, that will be moved.
     * @param posX X coordinate of destination.
     * @param posY Y coordinate of destination.
     */
    @MessageMapping("/move")
    public void moveUnit(Unit unit, double posX, double posY){
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
     * @param attacker Object of Unit type. This unit will deal damage toward defender.
     * @param defender Object of Unit type. This unit will receive dealt damage.
     */
    @MessageMapping("/attack")
    public void attackUnit(Unit attacker, Unit defender){
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
