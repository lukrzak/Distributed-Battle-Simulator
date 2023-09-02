package com.dbs.exceptions;

public class GameNotFoundException extends NullPointerException {

    public GameNotFoundException(String gameId) {
        super("Game with id=" + gameId + " does not exist");
    }
}
