package com.dbs.dbs.models.units;

import com.dbs.dbs.enumerations.UnitEnum;

/**
 * UnitFactory returns new instances of Unit type.
 * Desired unit must be passed as parameter of UnitEnum type in static createUnit(UnitEnum type) method.
 */
public class UnitFactory {

    /**
     * Returns new instance of Unit type object.
     * @param type defined by UnitEnum
     * @return new instance of Unit type
     */
    public static Unit createUnit(UnitEnum type, Double posX, Double posY){
        return switch (type) {
            case ARCHER -> new Archer(posX, posY);
            case FOOTMAN -> new Footman(posX, posY);
            case HEAVY_FOOTMAN -> new HeavyFootman(posX, posY);
            case KNIGHT -> new Knight(posX, posY);
            case PIKEMAN -> new Pikeman(posX, posY);
        };
    }
}
