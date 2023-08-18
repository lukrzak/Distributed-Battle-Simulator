package com.dbs.models.units;

import com.dbs.models.Player;
import lombok.Getter;
import lombok.Setter;

/**
 * Base of every unit in game.
 * All unit types must extend this class in order to work.
 */
@Getter
@Setter
public abstract class Unit {

    private long id;
    private String name;
    private double range;
    private double speed;
    private double damage;
    private double health;
    private int cost;
    private double positionX;
    private double positionY;
    private Thread moveTask;
    private Thread attackTask;
    private Player player;

    public Unit(String name, double range, double speed, double damage, double health, int cost) {
        this.name = name;
        this.range = range;
        this.speed = speed;
        this.damage = damage;
        this.health = health;
        this.cost = cost;
    }
}
