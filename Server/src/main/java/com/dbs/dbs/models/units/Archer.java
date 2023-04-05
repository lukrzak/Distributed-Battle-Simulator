package com.dbs.dbs.models.units;

/**
 * Class that represents Archer unit.
 */
public class Archer extends Unit{

    public Archer(Long id, Double posX, Double posY) {
        super(id, "Archer", 40.0, 5.0, 6.0, 50.0, 75, posX, posY);
    }
}
