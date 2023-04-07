package com.login.dbs.LoginForm.services;

import com.login.dbs.LoginForm.models.User;
import com.login.dbs.LoginForm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public void registerUser(User user) throws Exception {
        User existingUser = userRepository.findByUsernameOrMail(user.getUsername(), user.getMail());
        if (existingUser != null) throw new Exception("Username or email already taken");
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean authenticateUser(User user) throws Exception{
        User existingUser = userRepository.findByUsernameOrMail(user.getUsername(), user.getMail());
        if (existingUser == null) throw new Exception("User with given username or login does not exist");
        if (!encoder.matches(existingUser.getPassword(), user.getPassword())) throw new Exception("Password incorrect");
        return true;
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
