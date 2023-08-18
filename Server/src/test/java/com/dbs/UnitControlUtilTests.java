package com.dbs;

import com.dbs.enumerations.UnitType;
import com.dbs.models.Player;
import com.dbs.models.units.Unit;
import com.dbs.models.units.UnitFactory;
import com.dbs.utils.UnitControlUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UnitControlUtilTests {

    @Test
    void testUnitMovement() throws InterruptedException {
        Unit unitToMove = UnitFactory.createUnit(UnitType.KNIGHT, 1.0, 1.0, new Player());

        UnitControlUtil.moveUnit(unitToMove, 10.0, 10.0);

        assertEquals(10.0, unitToMove.getPositionX());
        assertEquals(10.0, unitToMove.getPositionY());
    }

    @Test
    void testUnitMovementWithIncorrectData() {
        Unit unitToMove = UnitFactory.createUnit(UnitType.KNIGHT, 1.0, 1.0, new Player());

        assertThrows(NullPointerException.class, () -> UnitControlUtil.moveUnit(null, 2.0, 2.0));
        assertThrows(IllegalArgumentException.class, () -> UnitControlUtil.moveUnit(unitToMove, -1.0, 1.0));
    }

    @Test
    void testUnitAttack() throws InterruptedException {
        Player p1 = new Player();
        Player p2 = new Player();
        Unit attacker = UnitFactory.createUnit(UnitType.FOOTMAN, 1.0, 1.0, p1);
        Unit defender = UnitFactory.createUnit(UnitType.ARCHER, 1.5, 1.5, p2);

        UnitControlUtil.attackUnit(attacker, defender);

        assertTrue(p1.getUnits().contains(attacker));
        assertFalse(p2.getUnits().contains(defender));
    }

    @Test
    void testUnitAttackOfTheSamePlayer() {
        Player p = new Player();
        Unit attacker = UnitFactory.createUnit(UnitType.FOOTMAN, 1.0, 1.0, p);
        Unit defender = UnitFactory.createUnit(UnitType.ARCHER, 1.5, 1.5, p);

        assertThrows(IllegalArgumentException.class, () -> UnitControlUtil.attackUnit(attacker, defender));
    }

    @Test
    void testUnitAttackIfOutOfRange() throws InterruptedException {
        Unit untouchedUnit = UnitFactory.createUnit(UnitType.FOOTMAN, 0.0, 0.0, new Player());
        Unit attacker = UnitFactory.createUnit(UnitType.FOOTMAN, 1.0, 1.0, new Player());
        Unit defender = UnitFactory.createUnit(UnitType.FOOTMAN, 50.0, 50.0, new Player());

        UnitControlUtil.attackUnit(attacker, defender);

        assertEquals(untouchedUnit.getHealth(), defender.getHealth());
    }

    @Test
    void testCreatingUnits() throws InterruptedException {
        Player p1 = new Player();
        Player p2 = new Player();
        int initialNumberOfUnits = p1.getUnits().size();

        Unit u1 = UnitControlUtil.createNewUnit(UnitType.FOOTMAN, 1.0, 1.0, p1);
        Unit u2 = UnitControlUtil.createNewUnit(UnitType.FOOTMAN, 1.0, 1.0, p1);
        Unit u3 = UnitControlUtil.createNewUnit(UnitType.FOOTMAN, 1.0, 1.0, p2);

        assertEquals(initialNumberOfUnits + 2, p1.getUnits().size());
        assertEquals(initialNumberOfUnits + 1, p2.getUnits().size());
        assertTrue(p1.getUnits().containsAll(List.of(u1, u2)));
        assertTrue(p2.getUnits().contains(u3));
    }
}
