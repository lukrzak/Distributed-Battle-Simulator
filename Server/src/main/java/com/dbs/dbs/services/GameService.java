package com.dbs.dbs.services;

import com.dbs.dbs.enumerations.UnitEnum;
import com.dbs.dbs.exceptions.UnitDoesntExistException;
import com.dbs.dbs.models.Game;
import com.dbs.dbs.models.units.Unit;
import com.dbs.dbs.models.units.UnitFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Stream;

import static java.lang.Math.*;

@Service
public class GameService{

    @Autowired
    private final UnitService unitService;
    @Autowired
    private final Game game;

    public GameService(UnitService unitService, Game game) {
        this.unitService = unitService;
        this.game = game;
    }

    public void moveUnit(Unit unit, Double newX, Double newY) throws InterruptedException {
        double angle = getMovingDirectionAngle(unit, newX, newY);
        double distance = sqrt(pow(unit.getPositionX() - newX, 2) + pow(unit.getPositionY() - newY, 2));
        double distanceTraveled = 0;

        while(distanceTraveled < distance){
            distanceTraveled += sqrt(pow(sin(angle), 2) + pow(cos(angle), 2));
            if(distanceTraveled >= distance){
                unit.setPositionX(newX);
                unit.setPositionY(newY);
            }
            else{
                unit.setPositionY(unit.getPositionY() + sin(angle));
                unit.setPositionX(unit.getPositionX() + cos(angle));
            }

            System.out.println("Unit " + unit.getName() + " at pos (" + unit.getPositionX() + "," + unit.getPositionY() + ")");
            Thread.sleep((int)(1000 / unit.getSpeed()));
        }
    }

    public double getMovingDirectionAngle(Unit unit, Double newX, Double newY){
        return Math.atan2((newY - unit.getPositionY()), (newX - unit.getPositionX()));
    }

    public void attackUnit(Unit attacker, Unit defender) throws InterruptedException {
        double distance = sqrt(pow(attacker.getPositionX() - defender.getPositionX(), 2)
                + pow(attacker.getPositionY() - defender.getPositionY(), 2));
        double damageFactor = unitService.getCounterFactor(attacker.getClass(), defender.getClass());

        while(distance <= attacker.getRange()){
            double damage = attacker.getDamage() * damageFactor;
            defender.setHealth(defender.getHealth() - damage);
            distance = sqrt(pow(attacker.getPositionX() - defender.getPositionX(), 2)
                    + pow(attacker.getPositionY() - defender.getPositionY(), 2));

            System.out.println(attacker.getName() + " attacked " + defender.getName() + " for " + damage + " damage");
            System.out.println("Defender " + defender.getName() + " has now " + defender.getHealth() + " health");
            Thread.sleep(500);
        }
        System.out.println("Out of range");
    }


    public Unit getUnitOfGivenId(Long id) throws UnitDoesntExistException {
        for (Unit unit: Stream.concat(game.getPlayerA().stream(), game.getPlayerB().stream()).toList()){
            if (Objects.equals(unit.getId(), id)) return unit;
        }
        throw new UnitDoesntExistException();
    }

    public void createUnit(UnitEnum type, double posX, double posY, boolean player) throws InterruptedException {
        Thread.sleep(2500);
        Unit newUnit = UnitFactory.createUnit(type, posX, posY);
        if(player) game.getPlayerA().add(newUnit);
        else game.getPlayerB().add(newUnit);
    }
}
