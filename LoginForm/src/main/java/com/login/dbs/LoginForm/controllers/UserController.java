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
    public void registerUser(@RequestBody User user) throws Exception {
        userService.registerUser(user);
    }

    @PostMapping("/authenticate")
    public User authenticateUser(@RequestBody User user) throws Exception {
        return userService.authenticateUser(user);
    }

    @PostMapping("/password-change")
    public void changePassword(@RequestBody User user, @RequestBody String newPassword) throws Exception {
        userService.changePassword(user, newPassword);
    }

    @PostMapping("/password-recovery")
    public void recoverPassword(String email){

    }
}
