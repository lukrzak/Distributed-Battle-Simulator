package com.login.dbs.LoginForm.controllers;

import com.login.dbs.LoginForm.dtos.BasicUserInfoDto;
import com.login.dbs.LoginForm.dtos.ChangeUserPasswordDto;
import com.login.dbs.LoginForm.exceptions.UserAlreadyExistException;
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
    public ResponseEntity<String> registerUser(@RequestBody BasicUserInfoDto userToCreate) {
        try {
            userService.registerUser(userToCreate);
        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>("User with given email or username already exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User " + userToCreate.username() + " created successfully", HttpStatus.OK);
    }

    @PostMapping("/authentication")
    public ResponseEntity<Boolean> authenticateUser(@RequestBody BasicUserInfoDto userToAuthenticate) {
        return new ResponseEntity<>(userService.authenticateUser(userToAuthenticate), HttpStatus.OK);
    }

    @PostMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody ChangeUserPasswordDto changeUserPasswordDto) {
        try {
            userService.changePassword(changeUserPasswordDto);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Password is incorrect", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Password has been changed", HttpStatus.OK);
    }

    @PostMapping("/password/recovery")
    public ResponseEntity<String> recoverPassword(@RequestBody String email) {
        try {
            userService.recoverPassword(email);
        } catch (IOException e) {
            return new ResponseEntity<>("Email doesn't exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("New password has been generated - check your email", HttpStatus.OK);
    }
}
