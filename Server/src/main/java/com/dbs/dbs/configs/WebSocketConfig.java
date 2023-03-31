package com.dbs.dbs.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuration of Spring Boot WebSocket.
 * Endpoint: '/dbs-socket'
 * Simple Message Broker: '/topic'
 * Destination Prefix = '/app'
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Message Broker configuration.
     * @param config Instance of MessageBrokerRegistry class.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * Endpoint configuration.
     * @param registry Instance of StompEndpointRegistry class.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/dbs-socket").withSockJS();
        registry.addEndpoint("/dbs-socket").setAllowedOriginPatterns("*");
    }
}
