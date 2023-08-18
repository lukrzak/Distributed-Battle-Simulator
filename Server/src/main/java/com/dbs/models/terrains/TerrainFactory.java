package com.dbs.models.terrains;

import com.dbs.enumerations.TerrainType;

public class TerrainFactory {

    /**
     * Returns new instance of Terrain type object.
     *
     * @param type defined by TerrainEnum
     * @return new instance of Terrain type
     */
    public static Terrain createTerrain(TerrainType type) {
        return switch (type) {
            case FOREST -> new Forest();
            case LAKE -> new Lake();
            case MOUNTAIN -> new Mountain();
            case PLAINS -> new Plains();
            case SWAMP -> new Swamp();
        };
    }
}
