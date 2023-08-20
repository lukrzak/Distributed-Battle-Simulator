package com.login.dbs.LoginForm.exceptions;

import com.login.dbs.LoginForm.dtos.BasicUserInfoDto;

public class UserAlreadyExistException extends Exception {

    public UserAlreadyExistException(BasicUserInfoDto userToCreate) {
        super("User " + userToCreate.username() + " already exists");
    }
}
