package com.dbs;

import com.dbs.enumerations.UnitType;
import com.dbs.models.Player;
import com.dbs.models.units.Archer;
import com.dbs.models.units.Footman;
import com.dbs.models.units.Knight;
import com.dbs.models.units.Pikeman;
import com.dbs.models.units.Unit;
import com.dbs.services.UnitService;
import com.dbs.models.units.UnitFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UnitTests {

    @Mock
    private Player player;
    @Mock
    private UnitService unitService;

    @Test
    @Disabled
    @DisplayName("Counter factor calculation")
    void getCounterFactorTest() {
        assertEquals(unitService.getCounterFactor(Pikeman.class, Knight.class), 1.5);
        assertEquals(unitService.getCounterFactor(Archer.class, Footman.class), 1.1);
        assertEquals(unitService.getCounterFactor(Archer.class, Archer.class), 1);
    }

    @Test
    @DisplayName("Creation units from UnitFactory")
    void unitFactoryTest() {
        assertTrue(UnitFactory.createUnit(UnitType.ARCHER, 0.0, 0.0, player) instanceof Archer);
        assertNotNull(UnitFactory.createUnit(UnitType.FOOTMAN, 0.0, 0.0, player));
        assertNotNull(UnitFactory.createUnit(UnitType.PIKEMAN, 0.0, 0.0, player));
    }

    @Test
    @Disabled
    @DisplayName("Testing units health after attack")
    void attackTest() {
        Unit knight = UnitFactory.createUnit(UnitType.KNIGHT, 0.0, 0.0, player);
        Unit pikeman = UnitFactory.createUnit(UnitType.PIKEMAN, 0.0, 0.0, player);
        Unit footman = UnitFactory.createUnit(UnitType.FOOTMAN, 0.0, 0.0, player);
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
