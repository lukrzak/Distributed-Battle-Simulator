package com.dbs.dbs.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Game {
    public static Long id = 0L;
    private List<Player> players = new ArrayList<>();

    public Game() {
    }
}
