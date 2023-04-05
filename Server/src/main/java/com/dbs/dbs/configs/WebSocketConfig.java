package com.dbs.dbs.configs;

import com.dbs.dbs.controllers.GameController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

/**
 * Configuration of Spring Boot WebSocket handlers.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private final GameController gameController;

    public WebSocketConfig(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(gameWebSocketHandler(), "/app").setAllowedOriginPatterns("*");
    }

    @Bean
    public WebSocketHandler gameWebSocketHandler(){
        return new GameWebSocketHandler(gameController);
    }

}
