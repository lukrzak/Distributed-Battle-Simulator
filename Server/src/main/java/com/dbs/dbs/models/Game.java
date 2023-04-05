package com.dbs.dbs.models;

import com.dbs.dbs.models.units.*;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class Game {
    private List<Unit> playerA = new LinkedList<>();
    private List<Unit> playerB = new LinkedList<>();

    public Game() {
        initializeUnits();

    }

    private void initializeUnits(){
        playerA.add(new Footman(1L,0.0, 0.0));
        playerA.add(new Footman(2L, 1.0, 1.0));
        playerA.add(new Knight(3L, 1.0, 0.0));
        playerA.add(new Archer(4L, 2.0, 0.0));
        playerA.add(new Pikeman(5L, 0.0, 2.0));
        playerA.add(new Archer(6L, 2.0, 2.0));

        playerB.add(new Footman(7L,100.0, 100.0));
        playerB.add(new Footman(8L, 101.0, 101.0));
        playerB.add(new Knight(9L, 101.0, 100.0));
        playerB.add(new Archer(10L, 102.0, 100.0));
        playerB.add(new Pikeman(11L, 100.0, 102.0));
        playerB.add(new Archer(12L, 102.0, 102.0));
    }
}
