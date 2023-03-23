package com.dbs.dbs.models.terrains;

public abstract class Terrain {
    private String name;
    private Double speedEffect;

    public Terrain(String name, Double speedEffect) {
        this.name = name;
        this.speedEffect = speedEffect;
    }
}
