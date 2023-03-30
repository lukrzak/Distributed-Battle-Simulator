package com.dbs.dbs.services;

import com.dbs.dbs.models.units.Unit;
import org.springframework.stereotype.Service;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

@Service
public class GameService {

    public GameService() {
    }

    public void moveUnit(Unit unit, int newX, int newY){
        double angle = getMovingDirectionAngle(unit, newX, newY);
        unit.setPositionX(unit.getPositionX() + cos(angle));
        unit.setPositionY(unit.getPositionY() + sin(angle));
    }

    public double getMovingDirectionAngle(Unit unit, int newX, int newY){
        return Math.atan2((newY - unit.getPositionY()), (newX - unit.getPositionX()));
    }
}
