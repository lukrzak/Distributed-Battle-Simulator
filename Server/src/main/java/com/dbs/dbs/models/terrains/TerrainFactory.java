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
            case FOREST -> new Forest();
            case LAKE -> new Lake();
            case MOUNTAIN -> new Mountain();
            case PLAINS -> new Plains();
            case SWAMP -> new Swamp();
        };
    }

}
