package com.dbs.dbs.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

/**
 * Configuration of Spring Boot WebSocket.
 * Endpoint: '/dbs-socket'
 * Simple Message Broker: '/topic'
 * Destination Prefix = '/app'
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(moveWebSocketHandler(), "/app").setAllowedOriginPatterns("*");
    }

    @Bean
    public WebSocketHandler moveWebSocketHandler(){
        return new GameWebSocketHandler();
    }
}
