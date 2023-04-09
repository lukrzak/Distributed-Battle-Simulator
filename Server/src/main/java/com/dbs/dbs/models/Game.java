package com.dbs.dbs.models;

import com.dbs.dbs.enumerations.UnitEnum;
import com.dbs.dbs.models.units.*;
import lombok.Data;

import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class Game {
    private CopyOnWriteArrayList<Unit> playerA = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<Unit> playerB = new CopyOnWriteArrayList<>();
    public static Long id = 0L;

    public Game() {
        initializeUnits();
    }

    public void initializeUnits(){
        playerA.add(UnitFactory.createUnit(UnitEnum.FOOTMAN, 1.0, 1.0));
        playerA.add(UnitFactory.createUnit(UnitEnum.FOOTMAN, 2.0, 1.0));
        playerA.add(UnitFactory.createUnit(UnitEnum.KNIGHT, 1.0, 2.0));
        playerA.add(UnitFactory.createUnit(UnitEnum.PIKEMAN, 4.0, 4.0));
        playerA.add(UnitFactory.createUnit(UnitEnum.ARCHER, 5.0, 5.0));

        playerB.add(UnitFactory.createUnit(UnitEnum.FOOTMAN, 100.0, 100.0));
        playerB.add(UnitFactory.createUnit(UnitEnum.FOOTMAN, 101.0, 100.0));
        playerB.add(UnitFactory.createUnit(UnitEnum.KNIGHT, 100.0, 101.0));
        playerB.add(UnitFactory.createUnit(UnitEnum.PIKEMAN, 101.0, 101.0));
        playerB.add(UnitFactory.createUnit(UnitEnum.ARCHER, 102.0, 100.0));
    }
}
