package com.dbs.dbs.models.units;

/**
 * Class that represents Knight unit.
 */
public class Knight extends Unit{

    public Knight(Long id, Double posX, Double posY) {
        super(id, "Knight", 7.0, 15.0, 20.0, 110.0, 150, posX, posY);
    }
}
