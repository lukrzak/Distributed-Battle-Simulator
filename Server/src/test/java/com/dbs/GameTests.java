package com.dbs;

import com.dbs.controllers.GameController;
import com.dbs.enumerations.UnitType;
import com.dbs.models.Game;
import com.dbs.models.Player;
import com.dbs.models.units.Unit;
import com.dbs.models.units.UnitFactory;
import com.dbs.services.GameService;
import com.dbs.services.UnitService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class GameTests {

    Game game = new Game();
    Player player = new Player();
    GameService gameService = new GameService(new UnitService(), game);
    GameController gameController = new GameController(gameService);

//    @Test
//    void getMovingDirectionAngleTest() {
//        Unit unit = UnitFactory.createUnit(UnitEnum.FOOTMAN, 0.0, 0.0, player);
//        assertTrue(cos(gameService.getMovingDirectionAngle(unit, 1.0, 1.0)) > 0);
//        assertTrue(sin(gameService.getMovingDirectionAngle(unit, 1.0, 1.0)) > 0);
//
//        assertTrue(cos(gameService.getMovingDirectionAngle(unit, 1.0, -1.0)) > 0);
//        assertTrue(sin(gameService.getMovingDirectionAngle(unit, 1.0, -1.0)) < 0);
//
//        assertTrue(cos(gameService.getMovingDirectionAngle(unit, -1.0, 1.0)) < 0);
//        assertTrue(sin(gameService.getMovingDirectionAngle(unit, -1.0, 1.0)) > 0);
//
//        assertTrue(cos(gameService.getMovingDirectionAngle(unit, -1.0, -1.0)) < 0);
//        assertTrue(sin(gameService.getMovingDirectionAngle(unit, -1.0, -1.0)) < 0);
//    }

    @Test
    @Disabled
    void attackAndRunSimulation() throws InterruptedException {
        Unit attacker = UnitFactory.createUnit(UnitType.FOOTMAN, 0.0, 0.0, player);
        Unit defender = UnitFactory.createUnit(UnitType.PIKEMAN, 0.0, 1.0, player);

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
            gameService.createUnit(UnitType.ARCHER, 10.0, 10.0, player);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        assertNotNull(gameService.getUnitOfGivenId(numberOfStartingUnits));
    }
}
