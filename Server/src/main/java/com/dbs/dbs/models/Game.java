package com.dbs.dbs.models;

import com.dbs.dbs.enumerations.UnitEnum;
import com.dbs.dbs.models.units.*;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class Game {
    private List<Unit> playerA = new LinkedList<>();
    private List<Unit> playerB = new LinkedList<>();
    public static Long id = 0L;

    public Game() {

    }

    public void initializeUnits(){
        playerA.add(UnitFactory.createUnit(UnitEnum.FOOTMAN, 1.0, 1.0));
        playerA.add(UnitFactory.createUnit(UnitEnum.FOOTMAN, 2.0, 2.0));
        playerA.add(UnitFactory.createUnit(UnitEnum.KNIGHT, 3.0, 3.0));
        playerA.add(UnitFactory.createUnit(UnitEnum.PIKEMAN, 4.0, 4.0));
        playerA.add(UnitFactory.createUnit(UnitEnum.ARCHER, 5.0, 5.0));

        playerB.add(UnitFactory.createUnit(UnitEnum.FOOTMAN, 100.0, 100.0));
        playerB.add(UnitFactory.createUnit(UnitEnum.FOOTMAN, 101.0, 100.0));
        playerB.add(UnitFactory.createUnit(UnitEnum.KNIGHT, 100.0, 101.0));
        playerB.add(UnitFactory.createUnit(UnitEnum.PIKEMAN, 101.0, 101.0));
        playerB.add(UnitFactory.createUnit(UnitEnum.ARCHER, 102.0, 100.0));
    }
}
