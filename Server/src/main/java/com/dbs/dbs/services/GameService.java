package com.dbs.dbs.services;

import com.dbs.dbs.models.units.Unit;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static java.lang.Math.*;

@Service
public class GameService{

    public GameService() {
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

}
