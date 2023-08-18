package com.dbs.dbs;

import com.dbs.dbs.enumerations.UnitEnum;
import com.dbs.dbs.models.Player;
import com.dbs.dbs.models.units.Archer;
import com.dbs.dbs.models.units.Footman;
import com.dbs.dbs.models.units.Knight;
import com.dbs.dbs.models.units.Pikeman;
import com.dbs.dbs.models.units.Unit;
import com.dbs.dbs.services.UnitService;
import com.dbs.dbs.models.units.UnitFactory;
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
        assertTrue(UnitFactory.createUnit(UnitEnum.ARCHER, 0.0, 0.0, player) instanceof Archer);
        assertNotNull(UnitFactory.createUnit(UnitEnum.FOOTMAN, 0.0, 0.0, player));
        assertNotNull(UnitFactory.createUnit(UnitEnum.PIKEMAN, 0.0, 0.0, player));
    }

    @Test
    @Disabled
    @DisplayName("Testing units health after attack")
    void attackTest() {
        Unit knight = UnitFactory.createUnit(UnitEnum.KNIGHT, 0.0, 0.0, player);
        Unit pikeman = UnitFactory.createUnit(UnitEnum.PIKEMAN, 0.0, 0.0, player);
        Unit footman = UnitFactory.createUnit(UnitEnum.FOOTMAN, 0.0, 0.0, player);
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
