package com.dbs.dbs;

import com.dbs.dbs.controllers.GameController;
import com.dbs.dbs.enumerations.UnitEnum;
import com.dbs.dbs.models.units.*;
import com.dbs.dbs.services.GameService;
import com.dbs.dbs.services.UnitService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameTests {

    GameService gameService = new GameService(new UnitService());
    GameController gameController = new GameController(gameService);

    @Test
    void getMovingDirectionAngleTest(){
        Unit unit = new Footman(0.0, 0.0);
        assertTrue(cos(gameService.getMovingDirectionAngle(unit, 1.0, 1.0)) > 0);
        assertTrue(sin(gameService.getMovingDirectionAngle(unit, 1.0, 1.0)) > 0);

        assertTrue(cos(gameService.getMovingDirectionAngle(unit, 1.0, -1.0)) > 0);
        assertTrue(sin(gameService.getMovingDirectionAngle(unit, 1.0, -1.0)) < 0);

        assertTrue(cos(gameService.getMovingDirectionAngle(unit, -1.0, 1.0)) < 0);
        assertTrue(sin(gameService.getMovingDirectionAngle(unit, -1.0, 1.0)) > 0);

        assertTrue(cos(gameService.getMovingDirectionAngle(unit, -1.0, -1.0)) < 0);
        assertTrue(sin(gameService.getMovingDirectionAngle(unit, -1.0, -1.0)) < 0);
    }

    @Test
    void attackAndRunSimulation() throws InterruptedException {
        Unit attacker = UnitFactory.createUnit(UnitEnum.FOOTMAN, 0.0, 0.0);
        Unit defender = UnitFactory.createUnit(UnitEnum.PIKEMAN, 0.0, 1.0);

        System.out.println(attacker.getName() + " initiated fight with " + defender.getName());
        System.out.println("--------------------");
        gameController.attackUnit(attacker, defender);

        Thread.sleep(2000);
        System.out.println("--------------------");
        System.out.println(defender.getName() + " decided to flee after 2 seconds!");
        System.out.println("--------------------");
        gameController.moveUnit(defender, 10.0, 10.0);

        System.out.println("--------------------");
        System.out.println(attacker.getName() + " started running after " + defender.getName());
        System.out.println("--------------------");
        gameController.moveUnit(attacker, 10.0, 10.0);

        Thread.sleep(10000);
    }
}
