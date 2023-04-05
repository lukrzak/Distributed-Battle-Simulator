package com.dbs.dbs.configs;

import com.dbs.dbs.models.Game;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;

@Configuration
public class BeanConfiguration {

    @Bean
    public Game game(){
        return new Game();
    }

    @Bean
    public static WebSocketHandler gameWebSocketHandler(){
        return new GameWebSocketHandler();
    }

}
