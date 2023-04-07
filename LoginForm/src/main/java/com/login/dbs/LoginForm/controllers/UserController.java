package com.login.dbs.LoginForm.controllers;

import com.login.dbs.LoginForm.models.User;
import com.login.dbs.LoginForm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dbs/login")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody User user){
        userService.registerUser(user);
    }

    @PostMapping("/authenticate")
    public void authenticateUser(@RequestBody User user){
        userService.authenticateUser(user);
    }
}
