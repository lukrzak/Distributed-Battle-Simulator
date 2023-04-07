package com.dbs.dbs.exceptions;

public class UnitDoesntExistException extends Exception{
    public UnitDoesntExistException() {
        super("Unit with given id does not exist");
    }
}
