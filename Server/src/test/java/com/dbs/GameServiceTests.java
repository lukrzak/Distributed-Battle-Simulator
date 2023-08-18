package com.dbs;

import com.dbs.enumerations.UnitType;
import com.dbs.models.Game;
import com.dbs.models.Player;
import com.dbs.models.units.Knight;
import com.dbs.models.units.Unit;
import com.dbs.models.units.UnitFactory;
import com.dbs.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameServiceTests {

    // TODO Replace Thread.sleep(1) with listener
    private GameService gameService = new GameService();
    private Game game;
    private Unit u1;
    private Unit u2;
    private Unit u3;
    private Player p1;
    private Player p2;

    @BeforeEach
    void setup() {
        game = new Game();
        p1 = new Player();
        p2 = new Player();
        game.getPlayers().add(p1);
        game.getPlayers().add(p2);
        u1 = UnitFactory.createUnit(UnitType.KNIGHT, 1.0, 1.0, p1);
        u2 = UnitFactory.createUnit(UnitType.ARCHER, 1.5, 1.5, p2);
        u3 = UnitFactory.createUnit(UnitType.ARCHER, 1.0, 1.0, p1);
    }

    @Test
    void testMovingTwoUnitsAtOnce() throws InterruptedException {
        double distanceToTravel = 17.3 - 1.0;
        long estimatedTravelTime = (long) Math.ceil(distanceToTravel * 1000 / u2.getSpeed());
        long epsilon = 70;

        gameService.moveUnit(u1, 10.0, 3.0);
        gameService.moveUnit(u2, 17.3, 1.0);

        Thread.sleep(estimatedTravelTime + epsilon);

        assertEquals(10.0, u1.getPositionX());
        assertEquals(3.0, u1.getPositionY());
        assertEquals(17.3, u2.getPositionX());
        assertEquals(1.0, u2.getPositionY());
    }

    @Test
    void testChangingMovementDirection() throws InterruptedException {
        gameService.moveUnit(u1, 10.0, 20.0);
        Thread.sleep(150);
        gameService.moveUnit(u1, 20.0, 10.0);
        while (u1.getMoveTask() != null) Thread.sleep(1);

        assertEquals(20.0, u1.getPositionX());
        assertEquals(10.0, u1.getPositionY());
    }

    @Test
    void testTwoAttackingUnitsEachOther() throws InterruptedException {
        Unit unharmedKnight = new Knight();

        gameService.attackUnit(u1, u2);
        gameService.attackUnit(u2, u1);
        while (u2.getHealth() > 0) Thread.sleep(1);

        assertTrue(p1.getUnits().contains(u1));
        assertFalse(p2.getUnits().contains(u2));
        assertTrue(unharmedKnight.getHealth() > u1.getHealth());
    }

    @Test
    void testAttackingUnitByTwoOpponents() throws InterruptedException {
        gameService.attackUnit(u1, u2);
        gameService.attackUnit(u3, u2);
        while (u2.getHealth() > 0) Thread.sleep(1);

        assertFalse(p2.getUnits().contains(u2));
        assertTrue(p1.getUnits().contains(u1));
        assertTrue(p1.getUnits().contains(u3));
    }

    @Test
    void testMovingAndAttackingAtOnceWithoutLosingRange() throws InterruptedException {
        gameService.attackUnit(u2, u3);
        gameService.moveUnit(u2, 20.0, 20.0);
        gameService.moveUnit(u3, 20.0, 20.0);
        while (u3.getHealth() > 0 || u2.getMoveTask() != null) Thread.sleep(1);

        assertEquals(20.0, u2.getPositionX());
        assertEquals(20.0, u2.getPositionY());
        assertTrue(p2.getUnits().contains(u2));
        assertFalse(p1.getUnits().contains(u3));
    }

    @Test
    void testMovingAndAttackingAtOnceWithLosingRange() throws InterruptedException {
        gameService.attackUnit(u1, u2);
        gameService.moveUnit(u1, 20.0, 20.0);
        gameService.moveUnit(u2, 20.0, 20.0);
        while (u1.getMoveTask() != null || u2.getMoveTask() != null) Thread.sleep(1);

        assertEquals(20.0, u1.getPositionX());
        assertEquals(20.0, u1.getPositionY());
        assertTrue(p1.getUnits().contains(u1));
        assertTrue(p2.getUnits().contains(u2));
    }

    @Test
    void testCreatingUnits() throws InterruptedException {
        int player1NumberOfUnits = p1.getUnits().size();
        int player2NumberOfUnits = p2.getUnits().size();

        gameService.createUnit(UnitType.ARCHER, 1.0, 1.0, p1);
        gameService.createUnit(UnitType.ARCHER, 1.0, 1.0, p2);
        gameService.createUnit(UnitType.ARCHER, 1.0, 1.0, p1);
        Thread.sleep(3500);

        assertEquals(player1NumberOfUnits + 2, p1.getUnits().size());
        assertEquals(player2NumberOfUnits + 1, p2.getUnits().size());
    }

    @Test
    void testGettingUnitsById() {
        long firstUnitId = p1.getUnits().get(0).getId();

        Optional<Unit> u1 = gameService.getUnitOfGivenId(firstUnitId, game);
        Optional<Unit> u2 = gameService.getUnitOfGivenId(-1L, game);

        assertTrue(u1.isPresent());
        assertFalse(u2.isPresent());
        assertTrue(p1.getUnits().contains(u1.get()));
    }
}
