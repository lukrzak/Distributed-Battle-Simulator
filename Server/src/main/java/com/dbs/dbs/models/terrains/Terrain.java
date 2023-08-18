package com.dbs.dbs.models.terrains;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Terrain {

    private String name;
    private Double speedEffect;
    private Double rangeEffect;

    public Terrain(String name, Double speedEffect, Double rangeEffect) {
        this.name = name;
        this.speedEffect = speedEffect;
        this.rangeEffect = rangeEffect;
    }
}
