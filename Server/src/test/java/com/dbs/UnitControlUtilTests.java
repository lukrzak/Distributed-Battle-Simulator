package com.dbs;

import com.dbs.enumerations.UnitType;
import com.dbs.models.Player;
import com.dbs.models.units.Unit;
import com.dbs.models.units.UnitFactory;
import com.dbs.utils.UnitControlUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class UnitControlUtilTests {

    @Test
    void testUnitMovement() {
        Unit unitToMove = UnitFactory.createUnit(UnitType.KNIGHT, 1.0, 1.0, new Player());

        try {
            UnitControlUtil.moveUnit(unitToMove, 10.0, 10.0);
        } catch (InterruptedException e) {
            fail("Unexpected interrupt while moving");
        }

        assertEquals(unitToMove.getPositionX(), 10.0);
        assertEquals(unitToMove.getPositionY(), 10.0);
    }

    @Test
    void testUnitMovementWithIncorrectData() {
        Unit unitToMove = UnitFactory.createUnit(UnitType.KNIGHT, 1.0, 1.0, new Player());

        assertThrows(NullPointerException.class, () -> UnitControlUtil.moveUnit(null, 2.0, 2.0));
        assertThrows(IllegalArgumentException.class, () -> UnitControlUtil.moveUnit(unitToMove, -1.0, 1.0));
    }

    @Test
    void testUnitAttack() {
        Player p1 = new Player();
        Player p2 = new Player();
        Unit attacker = UnitFactory.createUnit(UnitType.FOOTMAN, 1.0, 1.0, p1);
        Unit defender = UnitFactory.createUnit(UnitType.ARCHER, 1.5, 1.5, p2);

        try {
            UnitControlUtil.attackUnit(attacker, defender);
        } catch (InterruptedException e) {
            fail("Unexpected interrupt while attacking");
        }

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
    void testUnitAttackIfOutOfRange() {
        Unit untouchedUnit = UnitFactory.createUnit(UnitType.FOOTMAN, 0.0, 0.0, new Player());
        Unit attacker = UnitFactory.createUnit(UnitType.FOOTMAN, 1.0, 1.0, new Player());
        Unit defender = UnitFactory.createUnit(UnitType.FOOTMAN, 50.0, 50.0, new Player());

        try {
            UnitControlUtil.attackUnit(attacker, defender);
        } catch (InterruptedException e) {
            fail("Unexpected interrupt while attacking");
        }

        assertEquals(untouchedUnit.getHealth(), defender.getHealth());
    }
}
