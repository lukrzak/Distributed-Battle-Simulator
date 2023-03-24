package com.dbs.dbs.models.units;

import lombok.Data;

/**
 * Base of every unit in game.
 * Must be extended in order to work.
 */
@Data
public abstract class Unit {
    private String name;
    private Double range;
    private Double speed;
    private Double damage;
    private Double health;
    private Integer cost;

    public Unit(String name, Double range, Double speed, Double damage, Double health, Integer cost) {
        this.name = name;
        this.range = range;
        this.speed = speed;
        this.damage = damage;
        this.health = health;
        this.cost = cost;
    }

}
