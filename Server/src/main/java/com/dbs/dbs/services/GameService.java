package com.dbs.dbs.services;

import com.dbs.dbs.models.units.Unit;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    public GameService() {
    }

    public void moveUnit(Unit unit, int newX, int newY){
        double angle = getMovingDirectionAngle(unit, newX, newY);
    }

    public double getMovingDirectionAngle(Unit unit, int newX, int newY){
        return Math.atan((double)(newY - unit.getPositionY())/(newX - unit.getPositionX()));
    }
}
