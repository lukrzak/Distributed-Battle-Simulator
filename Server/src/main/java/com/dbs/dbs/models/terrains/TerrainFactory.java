package com.dbs.dbs.models.terrains;

import com.dbs.dbs.enumerations.TerrainEnum;

/**
 * TerrainFactory returns new instances of Terrain type.
 * Desired terrain must be passed as parameter of TerrainEnum type in static createTerrain(TerrainEnum type) method.
 */
public class TerrainFactory {

    /**
     * Returns new instance of Terrain type object.
     * @param type definied by TerrainEnum
     * @return new instance of Terrain type
     */
    public static Terrain createTerrain(TerrainEnum type){
        return switch (type){
            case Forest -> new Forest();
            case Lake -> new Lake();
            case Mountain -> new Mountain();
            case Plains -> new Plains();
            case Swamp -> new Swamp();
        };
    }

}
