package com.dbs;

import com.dbs.enumerations.UnitType;
import com.dbs.models.Game;
import com.dbs.models.Player;
import com.dbs.models.units.Knight;
import com.dbs.models.units.Unit;
import com.dbs.models.units.UnitFactory;
import com.dbs.services.GameService;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameServiceTests {

    // TODO Replace Thread.sleep(1) with listener
    private GameService gameService = new GameService();

    @Test
    void testMovingTwoUnitsAtOnce() throws InterruptedException {
        Unit u1 = UnitFactory.createUnit(UnitType.KNIGHT, 1.0, 1.0, new Player());
        Unit u2 = UnitFactory.createUnit(UnitType.KNIGHT, 1.0, 1.0, new Player());
        double destinationCoordinate = 17.3;
        long estimatedTime = (long) Math.ceil(destinationCoordinate * 1000 / u2.getSpeed());

        gameService.moveUnit(u1, 10.0, 3.0);
        gameService.moveUnit(u2, destinationCoordinate, 1.0);

        Thread.sleep(estimatedTime);

        assertEquals(10.0, u1.getPositionX());
        assertEquals(3.0, u1.getPositionY());
        assertEquals(17.3, u2.getPositionX());
        assertEquals(1.0, u2.getPositionY());
    }

    @Test
    void testChangingMovementDirection() throws InterruptedException {
        Unit unit = UnitFactory.createUnit(UnitType.KNIGHT, 1.0, 1.0, new Player());

        gameService.moveUnit(unit, 10.0, 20.0);
        Thread.sleep(150);
        gameService.moveUnit(unit, 20.0, 10.0);
        while (unit.getMoveTask() != null) Thread.sleep(1);

        assertEquals(20.0, unit.getPositionX());
        assertEquals(10.0, unit.getPositionY());
    }

    @Test
    void testTwoAttackingUnitsAtOnce() throws InterruptedException {
        Player p1 = new Player();
        Player p2 = new Player();
        Unit unharmedKnight = new Knight();
        Unit knight = UnitFactory.createUnit(UnitType.KNIGHT, 1.0, 1.5, p1);
        Unit archer = UnitFactory.createUnit(UnitType.ARCHER, 1.5, 1.0, p2);

        gameService.attackUnit(knight, archer);
        gameService.attackUnit(archer, knight);
        while (archer.getHealth() > 0) Thread.sleep(1);

        assertTrue(p1.getUnits().contains(knight));
        assertFalse(p2.getUnits().contains(archer));
        assertTrue(unharmedKnight.getHealth() > knight.getHealth());
    }

    @Test
    void testMovingAndAttackingAtOnceWithoutLosingRange() throws InterruptedException {
        Player player1 = new Player();
        Player player2 = new Player();
        Unit u1 = UnitFactory.createUnit(UnitType.FOOTMAN, 1.0, 1.0, player1);
        Unit u2 = UnitFactory.createUnit(UnitType.FOOTMAN, 1.5, 1.5, player2);

        gameService.attackUnit(u1, u2);
        gameService.moveUnit(u1, 20.0, 20.0);
        gameService.moveUnit(u2, 20.0, 20.0);
        while (u2.getHealth() > 0 || u1.getMoveTask() != null) Thread.sleep(1);

        assertEquals(20.0, u1.getPositionX());
        assertEquals(20.0, u1.getPositionY());
        assertTrue(player1.getUnits().contains(u1));
        assertFalse(player2.getUnits().contains(u2));
    }

    @Test
    void testMovingAndAttackingAtOnceWithLosingRange() throws InterruptedException {
        Player player1 = new Player();
        Player player2 = new Player();
        Unit u1 = UnitFactory.createUnit(UnitType.FOOTMAN, 1.0, 1.0, player1);
        Unit u2 = UnitFactory.createUnit(UnitType.KNIGHT, 1.5, 1.5, player2);

        gameService.attackUnit(u1, u2);
        gameService.moveUnit(u1, 20.0, 20.0);
        gameService.moveUnit(u2, 20.0, 20.0);
        while (u1.getMoveTask() != null || u2.getMoveTask() != null) Thread.sleep(1);

        assertEquals(20.0, u1.getPositionX());
        assertEquals(20.0, u1.getPositionY());
        assertTrue(player1.getUnits().contains(u1));
        assertTrue(player2.getUnits().contains(u2));
    }

    @Test
    void testCreatingUnits() throws InterruptedException {
        Player p1 = new Player();
        Player p2 = new Player();
        int initialNumberOfUnits = p1.getUnits().size();

        gameService.createUnit(UnitType.ARCHER, 1.0, 1.0, p1);
        gameService.createUnit(UnitType.ARCHER, 1.0, 1.0, p2);
        gameService.createUnit(UnitType.ARCHER, 1.0, 1.0, p1);
        Thread.sleep(3000);

        assertEquals(initialNumberOfUnits + 2, p1.getUnits().size());
        assertEquals(initialNumberOfUnits + 1, p2.getUnits().size());
    }

    @Test
    void testGettingUnitsById() {
        Game game = new Game();
        Player p1 = new Player();
        Player p2 = new Player();
        game.getPlayers().add(p1);
        game.getPlayers().add(p2);
        Unit firstUnit = p1.getUnits().get(1);
        int totalUnits = p1.getUnits().size() + p2.getUnits().size();

        Optional<Unit> u1 = gameService.getUnitOfGivenId(1L, game);
        Optional<Unit> u2 = gameService.getUnitOfGivenId((long) (p1.getUnits().size() + 1), game);
        Optional<Unit> u3 = gameService.getUnitOfGivenId((long) (totalUnits + 1), game);

        assertTrue(u1.isPresent());
        assertTrue(u2.isPresent());
        assertTrue(u3.isEmpty());
        assertEquals(firstUnit, u1.get());
        assertTrue(p1.getUnits().contains(u1.get()));
        assertTrue(p2.getUnits().contains(u2.get()));
    }
}
