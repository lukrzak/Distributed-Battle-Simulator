package com.dbs.dbs.models;

import com.dbs.dbs.enumerations.UnitEnum;
import com.dbs.dbs.models.units.Unit;
import com.dbs.dbs.models.units.UnitFactory;
import lombok.Getter;

import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class Player {

    private Long id;
    private Double money;
    private CopyOnWriteArrayList<Unit> units = new CopyOnWriteArrayList<>();

    public Player() {
        money = 500.0;
        initializeUnits();
    }

    private void initializeUnits() {
        units.add(UnitFactory.createUnit(UnitEnum.FOOTMAN, 1.0, 1.0, this));
        units.add(UnitFactory.createUnit(UnitEnum.FOOTMAN, 2.0, 1.0, this));
        units.add(UnitFactory.createUnit(UnitEnum.KNIGHT, 1.0, 2.0, this));
        units.add(UnitFactory.createUnit(UnitEnum.PIKEMAN, 4.0, 4.0, this));
        units.add(UnitFactory.createUnit(UnitEnum.ARCHER, 5.0, 5.0, this));
    }
}
