package com.dbs.dbs;

import com.dbs.dbs.enumerations.UnitEnum;
import com.dbs.dbs.models.units.*;
import com.dbs.dbs.services.UnitService;
import com.dbs.dbs.utils.UnitFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UnitTests {

    UnitService unitService = new UnitService();

    @Test
    @DisplayName("Counter factor calculation")
    void getCounterFactorTest(){
        assertEquals(unitService.getCounterFactor(Pikeman.class, Knight.class), 1.5);
        assertNotEquals(unitService.getCounterFactor(Archer.class, Footman.class), 1.2);
        assertEquals(unitService.getCounterFactor(Archer.class, Archer.class), 1);
    }

    @Test
    @DisplayName("Creation units from UnitFactory")
    void unitFactoryTest(){
        assertTrue(UnitFactory.createUnit(UnitEnum.ARCHER, 0, 0) instanceof Archer);
        assertTrue(UnitFactory.createUnit(UnitEnum.KNIGHT, 0, 0) instanceof Unit);
        assertNotNull(UnitFactory.createUnit(UnitEnum.FOOTMAN,  0, 0));
        assertNotNull(UnitFactory.createUnit(UnitEnum.PIKEMAN,  0, 0));
    }

    @Test
    @DisplayName("Testing units health after attack")
    void attackTest(){
        Unit knight = new Knight(0,0);
        Unit pikeman = new Pikeman(0,0);
        Unit footman = new Footman(0,0);
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
