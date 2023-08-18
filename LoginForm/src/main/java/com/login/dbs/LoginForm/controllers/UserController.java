package com.login.dbs.LoginForm.controllers;

import com.login.dbs.LoginForm.dtos.PasswordChangeDto;
import com.login.dbs.LoginForm.dtos.RecoveryEmailDto;
import com.login.dbs.LoginForm.models.User;
import com.login.dbs.LoginForm.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dbs/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public void registerUser(@RequestBody User user) throws Exception {
        userService.registerUser(user);
    }

    @PostMapping("/authentication")
    public boolean authenticateUser(@RequestBody User user) throws Exception {
        return userService.authenticateUser(user);
    }

    @PostMapping("/password")
    public void changePassword(@RequestBody PasswordChangeDto passwordChangeDto) throws Exception {
        userService.changePassword(passwordChangeDto.user(), passwordChangeDto.newPassword());
    }

    @PostMapping("/password/recovery")
    public void recoverPassword(@RequestBody RecoveryEmailDto recoveryEmail) throws Exception {
        userService.recoverPassword(recoveryEmail.email());
    }
}
