package com.dbs.dbs.utils;

import com.dbs.dbs.models.*;

public class UnitFactory {
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
