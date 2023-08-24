package com.login.dbs.LoginForm.controllers;

import com.login.dbs.LoginForm.dtos.BasicUserInfoDto;
import com.login.dbs.LoginForm.dtos.ChangeUserPasswordDto;
import com.login.dbs.LoginForm.exceptions.UserAlreadyExistException;
import com.login.dbs.LoginForm.models.User;
import com.login.dbs.LoginForm.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.io.IOException;

@RestController
@RequestMapping("/dbs/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<String> registerUser(@RequestBody BasicUserInfoDto userToCreate) throws UserAlreadyExistException {
        User createdUser = userService.registerUser(userToCreate);
        return new ResponseEntity<>("User " + createdUser + " created successfully", HttpStatus.OK);
    }

    @PostMapping("/authentication")
    public ResponseEntity<Boolean> authenticateUser(@RequestBody BasicUserInfoDto userToAuthenticate) {
        return new ResponseEntity<>(userService.authenticateUser(userToAuthenticate), HttpStatus.OK);
    }

    @PostMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody ChangeUserPasswordDto changeUserPasswordDto) throws AuthenticationException {
        User userWithChangedPassword = userService.changePassword(changeUserPasswordDto);
        return new ResponseEntity<>("Password for " + userWithChangedPassword + " has been changed", HttpStatus.OK);
    }

    @PostMapping("/password/recovery")
    public ResponseEntity<String> recoverPassword(@RequestBody String email) throws AuthenticationException, IOException {
        User userWithRecoveredPassword = userService.recoverPassword(email);
        return new ResponseEntity<>("New password has been generated for " + userWithRecoveredPassword + " - check your email", HttpStatus.OK);
    }
}
