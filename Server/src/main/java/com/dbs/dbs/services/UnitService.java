package com.dbs.dbs.services;

import com.dbs.dbs.models.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UnitService {
    private final HashMap<Pair<Class<?>, Class<?>>, Double> counterFactor = new HashMap<>();
    private final HashMap<Pair<Class<?>, Class<?>>, Double> terrainFactor = new HashMap<>();

    public UnitService() {
        counterFactor.put(new ImmutablePair<>(Pikeman.class, Knight.class), 1.5);
        counterFactor.put(new ImmutablePair<>(Archer.class, Footman.class), 1.1);
        counterFactor.put(new ImmutablePair<>(HeavyFootman.class, Pikeman.class), 1.2);
        counterFactor.put(new ImmutablePair<>(Knight.class, Archer.class), 1.3);

        //terrainFactor.put(new ImmutablePair<>())
    }

    public Double getCounterFactor(Class<?> attacker, Class<?> defender){
        return counterFactor.get(new ImmutablePair<>(attacker, defender)) == null ?
                1 : counterFactor.get(new ImmutablePair<>(attacker, defender));
    }

    public Integer getTerrainFactor(Class<?> unit, Class<?> terrain){
        return 0;
    }

    public void attack(Unit attacker, Unit defender){

    }

    public void useSpecialAbility(Unit unit){

    }
}
