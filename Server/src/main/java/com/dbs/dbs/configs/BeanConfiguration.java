package com.dbs.dbs.configs;

import com.dbs.dbs.models.Game;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public Game game(){
        return new Game();
    }



}
