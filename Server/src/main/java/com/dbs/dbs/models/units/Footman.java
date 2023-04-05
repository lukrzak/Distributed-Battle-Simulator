package com.dbs.dbs.models.units;

/**
 * Class that represents Footman unit.
 */
public class Footman extends Unit{

    public Footman(Long id, Double posX, Double posY) {
        super(id, "Footman", 3.0, 6.0, 7.0, 80.0, 50, posX, posY);
    }
}
