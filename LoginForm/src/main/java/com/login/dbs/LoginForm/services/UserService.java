package com.login.dbs.LoginForm.services;

import com.login.dbs.LoginForm.models.User;
import com.login.dbs.LoginForm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user){

    }

    public void authenticateUser(User user){

    }

}
