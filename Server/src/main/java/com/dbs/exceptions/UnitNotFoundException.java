package com.dbs.exceptions;

public class UnitNotFoundException extends NullPointerException {

    public UnitNotFoundException(Long unitId) {
        super("Unit with id=" + unitId + " does not exist");
    }
}
