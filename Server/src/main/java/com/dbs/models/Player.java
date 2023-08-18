package com.dbs.models;

import com.dbs.enumerations.UnitType;
import com.dbs.models.units.Unit;
import com.dbs.models.units.UnitFactory;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class Player {

    private long id;
    private int money;
    private final List<Unit> units = new LinkedList<>();

    public Player() {
        money = 500;
        //initializeUnits();
    }

    private void initializeUnits() {
        UnitFactory.createUnit(UnitType.FOOTMAN, 1.0, 1.0, this);
        UnitFactory.createUnit(UnitType.FOOTMAN, 2.0, 1.0, this);
        UnitFactory.createUnit(UnitType.KNIGHT, 1.0, 2.0, this);
        UnitFactory.createUnit(UnitType.PIKEMAN, 4.0, 4.0, this);
        UnitFactory.createUnit(UnitType.ARCHER, 5.0, 5.0, this);
    }
}
