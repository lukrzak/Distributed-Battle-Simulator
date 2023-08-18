package com.dbs.models.terrains;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Terrain {

    private String name;
    private double speedEffect;
    private double rangeEffect;

    public Terrain(String name, double speedEffect, double rangeEffect) {
        this.name = name;
        this.speedEffect = speedEffect;
        this.rangeEffect = rangeEffect;
    }
}
