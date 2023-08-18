package com.dbs.utils;

import com.dbs.enumerations.UnitType;
import com.dbs.models.Player;
import com.dbs.models.units.Archer;
import com.dbs.models.units.Footman;
import com.dbs.models.units.HeavyFootman;
import com.dbs.models.units.Knight;
import com.dbs.models.units.Pikeman;
import com.dbs.models.units.Unit;
import com.dbs.models.units.UnitFactory;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class UnitControlUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(UnitControlUtil.class);
    private final static HashMap<Pair<Class<? extends Unit>, Class<? extends Unit>>, Double> counterDamageFactor = new HashMap<>();

    public UnitControlUtil() {
        counterDamageFactor.put(new ImmutablePair<>(Pikeman.class, Knight.class), 1.5);
        counterDamageFactor.put(new ImmutablePair<>(Archer.class, Footman.class), 1.1);
        counterDamageFactor.put(new ImmutablePair<>(HeavyFootman.class, Pikeman.class), 1.2);
        counterDamageFactor.put(new ImmutablePair<>(Knight.class, Archer.class), 1.3);
    }

    public static void moveUnit(Unit unit, double newX, double newY) throws InterruptedException {
        validateNewPosition(newX, newY);
        double angle = getMovingDirectionAngle(unit, newX, newY);
        double distance = sqrt(pow(unit.getPositionX() - newX, 2) + pow(unit.getPositionY() - newY, 2));
        double distanceTraveled = 0;

        while (distanceTraveled < distance) {
            distanceTraveled += sqrt(pow(sin(angle), 2) + pow(cos(angle), 2));
            if (distanceTraveled >= distance) {
                unit.setPositionX(newX);
                unit.setPositionY(newY);
                unit.setMoveTask(null);
            } else {
                unit.setPositionY(unit.getPositionY() + sin(angle));
                unit.setPositionX(unit.getPositionX() + cos(angle));
            }

            LOGGER.debug("Unit " + unit.getName() + " at pos (" + unit.getPositionX() + "," + unit.getPositionY() + ")");
            Thread.sleep((int) (1000 / unit.getSpeed()));
        }
    }

    public static void attackUnit(Unit attacker, Unit defender) throws InterruptedException {
        validateUnitsAttack(attacker, defender);
        synchronized (defender) {
            double distance = sqrt(pow(attacker.getPositionX() - defender.getPositionX(), 2)
                    + pow(attacker.getPositionY() - defender.getPositionY(), 2));
            if (distance > attacker.getRange()) {
                LOGGER.debug("Out of range");
                return;
            }

            double damageFactor = getCounterFactor(attacker.getClass(), defender.getClass());
            double damage = attacker.getDamage() * damageFactor;
            defender.setHealth(defender.getHealth() - damage);
            if (defender.getHealth() <= 0) {
                killUnit(defender);
                return;
            }

            LOGGER.debug(attacker.getName() + " attacked " + defender.getName() + " for " + damage + " damage");
        }

        LOGGER.debug("Defender " + defender.getName() + " has now " + defender.getHealth() + " health");
        Thread.sleep(500);
        attackUnit(attacker, defender);
    }

    public static void createNewUnit(UnitType type, double posX, double posY, Player player) throws InterruptedException {
        Thread.sleep(2500);
        UnitFactory.createUnit(type, posX, posY, player);
    }

    private static double getMovingDirectionAngle(Unit unit, Double newX, Double newY) {
        return Math.atan2((newY - unit.getPositionY()), (newX - unit.getPositionX()));
    }

    private static void validateNewPosition(double... positions) {
        for (double pos : positions)
            if (pos < 0.0)
                throw new IllegalArgumentException("Cannot move unit to position with negative coordinates");
    }

    private static void killUnit(Unit unit) {
        LOGGER.debug(unit.getName() + " has been defeated");
        unit.getPlayer().getUnits().remove(unit);
    }

    private static Double getCounterFactor(Class<? extends Unit> attacker, Class<? extends Unit> defender) {
        return counterDamageFactor.get(new ImmutablePair<>(attacker, defender)) == null
                ? 1
                : counterDamageFactor.get(new ImmutablePair<>(attacker, defender));
    }

    private static void validateUnitsAttack(Unit u1, Unit u2) {
        if (u1.getPlayer().equals(u2.getPlayer()))
            throw new IllegalArgumentException("Cannot attack unit of the same player");
    }
}
