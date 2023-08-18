package com.dbs.models;

import com.dbs.enumerations.UnitType;
import com.dbs.models.units.Unit;
import com.dbs.models.units.UnitFactory;
import lombok.Getter;

import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class Player {

    private long id;
    private int money;
    private final CopyOnWriteArrayList<Unit> units = new CopyOnWriteArrayList<>();

    public Player() {
        money = 500;
        initializeUnits();
    }

    private void initializeUnits() {
        units.add(UnitFactory.createUnit(UnitType.FOOTMAN, 1.0, 1.0, this));
        units.add(UnitFactory.createUnit(UnitType.FOOTMAN, 2.0, 1.0, this));
        units.add(UnitFactory.createUnit(UnitType.KNIGHT, 1.0, 2.0, this));
        units.add(UnitFactory.createUnit(UnitType.PIKEMAN, 4.0, 4.0, this));
        units.add(UnitFactory.createUnit(UnitType.ARCHER, 5.0, 5.0, this));
    }
}
