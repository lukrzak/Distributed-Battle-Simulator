package com.dbs.dbs.configs;

import com.dbs.dbs.models.Game;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Beans definitions.
 */
@Configuration
public class BeanConfiguration {

    /**
     * Creates Bean of Game type.
     * @return Instance of Game.
     */
    @Bean
    public Game game(){
        return new Game();
    }

}
