package com.dbs.dbs.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

/**
 * Configuration of Spring Boot WebSocket handlers.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(BeanConfiguration.gameWebSocketHandler(), "/app").setAllowedOriginPatterns("*");
    }

}
