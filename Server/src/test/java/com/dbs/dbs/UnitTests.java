package com.dbs.dbs;

import com.dbs.dbs.models.*;
import com.dbs.dbs.services.UnitService;
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
}
