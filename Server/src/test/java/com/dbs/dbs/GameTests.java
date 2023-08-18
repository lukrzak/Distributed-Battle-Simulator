package com.dbs.dbs;

import com.dbs.dbs.controllers.GameController;
import com.dbs.dbs.enumerations.UnitEnum;
import com.dbs.dbs.models.Game;
import com.dbs.dbs.models.Player;
import com.dbs.dbs.models.units.*;
import com.dbs.dbs.services.GameService;
import com.dbs.dbs.services.UnitService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class GameTests {

    Game game = new Game();
    Player player = new Player();
    GameService gameService = new GameService(new UnitService(), game);
    GameController gameController = new GameController(gameService);

    @Test
    void getMovingDirectionAngleTest() {
        Unit unit = UnitFactory.createUnit(UnitEnum.FOOTMAN, 0.0, 0.0, player);
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
    @Disabled
    void attackAndRunSimulation() throws InterruptedException {
        Unit attacker = UnitFactory.createUnit(UnitEnum.FOOTMAN, 0.0, 0.0, player);
        Unit defender = UnitFactory.createUnit(UnitEnum.PIKEMAN, 0.0, 1.0, player);

        gameController.attackUnit(attacker.getId(), defender.getId());

        Thread.sleep(2000);
        gameController.moveUnit(defender.getId(), 10.0, 10.0);
        gameController.moveUnit(attacker.getId(), 10.0, 10.0);

        Thread.sleep(10000);
    }

    @Test
    @Disabled
    void getUnitOfGivenIdTest() {
        assertNotNull(gameService.getUnitOfGivenId(1L));
        Long numberOfStartingUnits = Game.id;
        assertNotNull(gameService.getUnitOfGivenId(numberOfStartingUnits - 1));

        try {
            gameService.createUnit(UnitEnum.ARCHER, 10.0, 10.0, player);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        assertNotNull(gameService.getUnitOfGivenId(numberOfStartingUnits));
    }
}
