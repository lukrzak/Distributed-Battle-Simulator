package com.dbs.dbs.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

    @MessageMapping("/test")
    public void test(){
        System.out.println("Test");
    }
}
