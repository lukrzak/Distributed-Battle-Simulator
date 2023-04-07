package com.login.dbs.LoginForm;

import com.login.dbs.LoginForm.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTests {

    @Autowired
    private UserService gameService;

    @Test
    public void initialTest(){

    }

}
