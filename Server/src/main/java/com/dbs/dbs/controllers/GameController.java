package com.dbs.dbs.controllers;

import com.dbs.dbs.models.units.Unit;
import com.dbs.dbs.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GameController{

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @MessageMapping("/test")
    public void test(){
        System.out.println("Test");
    }

    @MessageMapping("/move")
    public void moveUnit(Unit unit, double posX, double posY){
        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    gameService.moveUnit(unit, posX, posY);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        newThread.start();
    }

}
