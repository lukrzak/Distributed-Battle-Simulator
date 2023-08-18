package com.dbs.dbs.models;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Game {

    public static Long id = 0L;
    private final List<Player> players = new ArrayList<>();
}
