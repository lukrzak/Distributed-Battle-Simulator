package com.dbs;

import com.dbs.controllers.GameController;
import com.dbs.enumerations.UnitType;
import com.dbs.exceptions.GameNotFoundException;
import com.dbs.exceptions.UnitNotFoundException;
import com.dbs.models.Game;
import com.dbs.models.Player;
import com.dbs.models.units.Unit;
import com.dbs.models.units.UnitFactory;
import com.dbs.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameControllerTests {

    private final GameService gameService = mock(GameService.class);
    private final GameController gameController = new GameController(gameService);
    private final int DAMAGE_COOLDOWN = 500;
    private Unit u1;
    private Unit u2;
    private Game game;
    private Player p1;
    private Player p2;

    @BeforeEach
    void setup() {
        p1 = new Player();
        p2 = new Player();
        game = new Game();
        game.getPlayers().add(p1);
        game.getPlayers().add(p2);
        u1 = UnitFactory.createUnit(UnitType.KNIGHT, 1.0, 1.0, p1);
        u2 = UnitFactory.createUnit(UnitType.ARCHER, 1.0, 1.0, p2);
        when(gameService.findGameById(eq("AAA"))).thenReturn(Optional.of(game));
        when(gameService.getUnitOfGivenId(eq(1L), any())).thenReturn(Optional.of(u1));
        when(gameService.getUnitOfGivenId(eq(2L), any())).thenReturn(Optional.of(u2));
        doCallRealMethod().when(gameService).moveUnit(any(), any(), any());
        doCallRealMethod().when(gameService).attackUnit(any(), any());
        doCallRealMethod().when(gameService).createUnit(any(), anyDouble(), anyDouble(), any());
    }

    @Test
    void testMovingUnit() throws InterruptedException {
        double distanceToTravel = 5.0 - 1.0;
        long estimatedTime = (long) Math.ceil(distanceToTravel * 1000 / u2.getSpeed());

        gameController.moveUnit(1L, 5.0, 5.0, "AAA");
        gameController.moveUnit(2L, 5.0, 5.0, "AAA");
        Thread.sleep(estimatedTime);

        assertEquals(5.0, u1.getPositionX());
    }

    @Test
    void testMovingUnitWithInvalidData() {
        assertThrows(GameNotFoundException.class, () -> gameController.moveUnit(1L, 5.0, 5.0, "BAD"));
        assertThrows(UnitNotFoundException.class, () -> gameController.moveUnit(3L, 5.0, 5.0, "AAA"));
    }

    @Test
    void testAttackingUnit() throws InterruptedException {
        long estimatedTimeToKillUnit = (long) Math.ceil(u2.getHealth() / u1.getDamage() * DAMAGE_COOLDOWN);

        gameController.attackUnit(1L, 2L, "AAA");
        Thread.sleep(estimatedTimeToKillUnit);

        assertTrue(u2.getHealth() < 0);
        assertTrue(p1.getUnits().contains(u1));
        assertFalse(p2.getUnits().contains(u2));
    }

    @Test
    void testAttackingWithInvalidData() {
        assertThrows(GameNotFoundException.class, () -> gameController.attackUnit(1L, 2L, "BAD"));
        assertThrows(UnitNotFoundException.class, () -> gameController.attackUnit(3L, 2L, "AAA"));
        assertThrows(UnitNotFoundException.class, () -> gameController.attackUnit(1L, 3L, "AAA"));
    }

    @Test
    void testCreatingUnit() throws InterruptedException {
        int initialNumberOfUnits = p1.getUnits().size();

        gameController.createUnit(UnitType.FOOTMAN, 2.0, 1.0, p2);
        gameController.createUnit(UnitType.FOOTMAN, 1.0, 1.0, p1);
        gameController.createUnit(UnitType.FOOTMAN, 3.0, 1.0, p2);
        Thread.sleep(3500);

        assertEquals(initialNumberOfUnits + 1, p1.getUnits().size());
        assertEquals(initialNumberOfUnits + 2, p2.getUnits().size());
    }
}
