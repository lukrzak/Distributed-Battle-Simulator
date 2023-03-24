package com.dbs.dbs.utils;

import com.dbs.dbs.enumerations.UnitEnum;
import com.dbs.dbs.models.units.*;

/**
 * UnitFactory returns new instances of Unit type.
 * Desired unit must be passed as parameter of UnitEnum type in static createUnit(UnitEnum type) method.
 */
public class UnitFactory {

    /**
     * Returns new instance of Unit type object.
     * @param type definied by UnitEnum
     * @return new instance of Unit type
     */
    public static Unit createUnit(UnitEnum type){
        return switch (type) {
            case ARCHER -> new Archer();
            case FOOTMAN -> new Footman();
            case HEAVY_FOOTMAN -> new HeavyFootman();
            case KNIGHT -> new Knight();
            case PIKEMAN -> new Pikeman();
        };
    }
}
