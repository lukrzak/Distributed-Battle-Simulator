package com.dbs.services;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class TerrainService {

    private final HashMap<Pair<Class<?>, Class<?>>, Double> terrainFactor = new HashMap<>();
}
