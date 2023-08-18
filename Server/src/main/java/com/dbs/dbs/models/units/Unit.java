package com.dbs.dbs.models.units;

import com.dbs.dbs.models.Player;
import lombok.Getter;
import lombok.Setter;

/**
 * Base of every unit in game.
 * All unit types must extend this class in order to work.
 */
@Getter
@Setter
public abstract class Unit {

    private Long id;
    private String name;
    private Double range;
    private Double speed;
    private Double damage;
    private Double health;
    private Integer cost;
    private Double positionX;
    private Double positionY;
    private Thread moveTask;
    private Thread attackTask;
    private Player player;

    public Unit(String name, Double range, Double speed, Double damage, Double health, Integer cost) {
        this.name = name;
        this.range = range;
        this.speed = speed;
        this.damage = damage;
        this.health = health;
        this.cost = cost;
    }
}
