package com.dbs.dbs.services;

import com.dbs.dbs.enumerations.UnitEnum;
import com.dbs.dbs.models.Game;
import com.dbs.dbs.models.Player;
import com.dbs.dbs.models.units.Unit;
import com.dbs.dbs.models.units.UnitFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.*;

@Service
public class GameService{

    @Autowired
    private final UnitService unitService;
    @Autowired
    private final Game game;
    @Value("${dbs.debug.info}")
    private boolean debug;

    public GameService(UnitService unitService, Game game) {
        this.unitService = unitService;
        this.game = game;
    }

    public void moveUnit(Unit unit, Double newX, Double newY) throws InterruptedException {
        if(unit == null) return;
        double angle = getMovingDirectionAngle(unit, newX, newY);
        double distance = sqrt(pow(unit.getPositionX() - newX, 2) + pow(unit.getPositionY() - newY, 2));
        double distanceTraveled = 0;

        while(distanceTraveled < distance){
            distanceTraveled += sqrt(pow(sin(angle), 2) + pow(cos(angle), 2));
            if(distanceTraveled >= distance){
                unit.setPositionX(newX);
                unit.setPositionY(newY);
                unit.setMoveTask(null);
            }
            else{
                unit.setPositionY(unit.getPositionY() + sin(angle));
                unit.setPositionX(unit.getPositionX() + cos(angle));
            }

            if(debug){
                System.out.println("Unit " + unit.getName() + " at pos (" + unit.getPositionX() + "," + unit.getPositionY() + ")");
            }
            Thread.sleep((int)(1000 / unit.getSpeed()));
        }
    }

    public double getMovingDirectionAngle(Unit unit, Double newX, Double newY){
        return Math.atan2((newY - unit.getPositionY()), (newX - unit.getPositionX()));
    }

    public void attackUnit(Long attackerId, Long defenderId) throws InterruptedException{
        Unit attacker;
        Unit defender;
        double damage;
        synchronized (this){
            attacker = getUnitOfGivenId(attackerId);
            defender = getUnitOfGivenId(defenderId);
            if(attacker == null || defender == null) return;

            double distance = sqrt(pow(attacker.getPositionX() - defender.getPositionX(), 2)
                    + pow(attacker.getPositionY() - defender.getPositionY(), 2));
            if(distance > attacker.getRange()) {
                System.out.println("Out of range");
                return;
            }
            double damageFactor = unitService.getCounterFactor(attacker.getClass(), defender.getClass());

            damage = attacker.getDamage() * damageFactor;
            defender.setHealth(defender.getHealth() - damage);
            if(defender.getHealth() <= 0) killUnit(defender);
        }

        if(debug){
            System.out.println(attacker.getName() + " attacked " + defender.getName() + " for " + damage + " damage");
            System.out.println("Defender " + defender.getName() + " has now " + defender.getHealth() + " health");
        }

        Thread.sleep(500);
        attackUnit(attackerId, defenderId);
    }


    public Unit getUnitOfGivenId(Long id){
        List<Unit> allUnits = game.getPlayers().stream()
                .flatMap(player -> player.getUnits().stream())
                .toList();
        for(Unit unit: allUnits){
            if(Objects.equals(unit.getId(), id)) return unit;
        }
        return null;
    }

    public void createUnit(UnitEnum type, double posX, double posY, Player player) throws InterruptedException {
        Thread.sleep(2500);
        Unit newUnit = UnitFactory.createUnit(type, posX, posY, player);
        player.getUnits().add(newUnit);
    }

    public void killUnit(Unit unit){
        if(debug) System.out.println(unit.getName() + " has been defeated");

        unit.getPlayer().getUnits().remove(unit);
    }
}
