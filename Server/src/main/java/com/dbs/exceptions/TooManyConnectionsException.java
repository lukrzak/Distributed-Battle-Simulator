package com.dbs.exceptions;

public class TooManyConnectionsException extends Exception {

    public TooManyConnectionsException() {
        super("Cannot establish connection - too many users are connected right now");
    }
}
