package com.dbs.dbs.models.units;

/**
 * Class that represents Pikeman unit.
 */
public class Pikeman extends Unit{

    public Pikeman(Long id, Double posX, Double posY) {
        super(id, "Pikeman", 10.0, 5.0, 15.0, 130.0, 100, posX, posY);
    }
}
