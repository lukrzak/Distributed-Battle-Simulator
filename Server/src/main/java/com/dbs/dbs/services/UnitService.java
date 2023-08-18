package com.dbs.dbs.services;

import com.dbs.dbs.models.units.Archer;
import com.dbs.dbs.models.units.Footman;
import com.dbs.dbs.models.units.HeavyFootman;
import com.dbs.dbs.models.units.Knight;
import com.dbs.dbs.models.units.Pikeman;
import com.dbs.dbs.models.units.Unit;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UnitService {
    
    private final HashMap<Pair<Class<? extends Unit>, Class<? extends Unit>>, Double> counterDamageFactor = new HashMap<>();

    public UnitService() {
        counterDamageFactor.put(new ImmutablePair<>(Pikeman.class, Knight.class), 1.5);
        counterDamageFactor.put(new ImmutablePair<>(Archer.class, Footman.class), 1.1);
        counterDamageFactor.put(new ImmutablePair<>(HeavyFootman.class, Pikeman.class), 1.2);
        counterDamageFactor.put(new ImmutablePair<>(Knight.class, Archer.class), 1.3);
    }

    public Double getCounterFactor(Class<? extends Unit> attacker, Class<? extends Unit> defender) {
        return counterDamageFactor.get(new ImmutablePair<>(attacker, defender)) == null
                ? 1
                : counterDamageFactor.get(new ImmutablePair<>(attacker, defender));
    }

    public void attack(Unit attacker, Unit defender) {
        double damageDealt = attacker.getDamage() * getCounterFactor(attacker.getClass(), defender.getClass());
        defender.setHealth(defender.getHealth() - damageDealt);
    }
}
