package com.dbs.models.units;

import com.dbs.enumerations.UnitType;
import com.dbs.models.Game;
import com.dbs.models.Player;

public class UnitFactory {

    /**
     * Returns new instance of Unit type object and assigns it to player.
     *
     * @param type defined by UnitEnum
     * @return new instance of Unit type
     */
    public static Unit createUnit(UnitType type, Double posX, Double posY, Player player) {
        Unit newUnit;
        switch (type) {
            case ARCHER -> newUnit = new Archer();
            case FOOTMAN -> newUnit = new Footman();
            case HEAVY_FOOTMAN -> newUnit = new HeavyFootman();
            case KNIGHT -> newUnit = new Knight();
            case PIKEMAN -> newUnit = new Pikeman();
            default -> newUnit = null;
        }
        newUnit.setId(Game.id++);
        newUnit.setPositionX(posX);
        newUnit.setPositionY(posY);
        newUnit.setPlayer(player);
        player.getUnits().add(newUnit);

        return newUnit;
    }
}
