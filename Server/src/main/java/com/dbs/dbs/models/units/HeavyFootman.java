package com.dbs.dbs.models.units;

/**
 * Class that represents Heavy Footman unit.
 */
public class HeavyFootman extends Unit{

    public HeavyFootman(Long id, Double posX, Double posY) {
        super(id, "Heavy Footman", 4.0, 4.0, 20.0, 150.0, 125, posX, posY);
    }
}
