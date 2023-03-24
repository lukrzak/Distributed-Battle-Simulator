package com.dbs.dbs;

import com.dbs.dbs.enumerations.UnitEnum;
import com.dbs.dbs.models.terrains.Swamp;
import com.dbs.dbs.models.units.*;
import com.dbs.dbs.services.UnitService;
import com.dbs.dbs.utils.UnitFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTests {

    UnitService unitService = new UnitService();

    @Test
    void getCounterFactorTest(){
        assertEquals(unitService.getCounterFactor(Pikeman.class, Knight.class), 1.5);
        assertNotEquals(unitService.getCounterFactor(Archer.class, Footman.class), 1.2);
        assertEquals(unitService.getCounterFactor(Archer.class, Archer.class), 1);
    }

    @Test
    void unitFactoryTest(){
        assertTrue(UnitFactory.createUnit(UnitEnum.ARCHER) instanceof Archer);
        assertTrue(UnitFactory.createUnit(UnitEnum.KNIGHT) instanceof Unit);
        assertNotNull(UnitFactory.createUnit(UnitEnum.FOOTMAN));
        assertNotNull(UnitFactory.createUnit(UnitEnum.PIKEMAN));
    }

    @Test
    void attackTest(){
        Unit knight = new Knight();
        Unit pikeman = new Pikeman();
        Unit footman = new Footman();
        double knightMaxHealth = knight.getHealth();
        double pikemanMaxHealth = pikeman.getHealth();
        unitService.attack(pikeman, knight);
        unitService.attack(footman, pikeman);

        assertEquals(knight.getHealth(),
                knightMaxHealth - pikeman.getDamage() * unitService.getCounterFactor(Pikeman.class, Knight.class));
        assertEquals(pikeman.getHealth(),
                pikemanMaxHealth - footman.getDamage() * unitService.getCounterFactor(Footman.class, Pikeman.class));
        assertTrue(knight.getHealth() < knightMaxHealth);
    }
}
