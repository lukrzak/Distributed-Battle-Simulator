package com.dbs.dbs.models;

import lombok.Data;

@Data
public abstract class Unit {
    private String name;
    private Integer range;
    private Integer speed;
    private Integer damage;
    private Integer health;
    private Integer cost;

    public Unit(String name, Integer range, Integer speed, Integer damage, Integer health, Integer cost) {
        this.name = name;
        this.range = range;
        this.speed = speed;
        this.damage = damage;
        this.health = health;
        this.cost = cost;
    }

    // Terrain factor
    // Attack method
    // Counter factor
    // Special ability
}