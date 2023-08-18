package com.dbs.configs;

import com.dbs.controllers.GameController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final GameController gameController;

    public WebSocketConfig(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(gameWebSocketHandler(), "/app").setAllowedOriginPatterns("*");
    }

    @Bean
    public WebSocketHandler gameWebSocketHandler() {
        return new GameWebSocketHandler(gameController);
    }
}
