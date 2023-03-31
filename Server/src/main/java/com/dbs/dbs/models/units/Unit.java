package com.dbs.dbs.models.units;

import lombok.Data;

/**
 * Base of every unit in game.
 * All unit types must extend this class in order to work.
 */
@Data
public abstract class Unit {
    private String name;
    private Double range;
    private Double speed;
    private Double damage;
    private Double health;
    private Integer cost;
    private Double positionX;
    private Double positionY;

    public Unit(String name, Double range, Double speed, Double damage, Double health, Integer cost, Double positionX, Double positionY) {
        this.name = name;
        this.range = range;
        this.speed = speed;
        this.damage = damage;
        this.health = health;
        this.cost = cost;
        this.positionX = positionX;
        this.positionY = positionY;
    }

}
