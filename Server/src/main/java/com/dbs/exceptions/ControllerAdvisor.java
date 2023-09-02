package com.dbs.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {

    private final Logger LOGGER = LoggerFactory.getLogger(ControllerAdvisor.class);

    @ExceptionHandler(UnitNotFoundException.class)
    public void handleUnitNotFoundException(UnitNotFoundException e) {
        LOGGER.error(e.getMessage());
        LOGGER.info(e.getCause().toString());
    }

    @ExceptionHandler(GameNotFoundException.class)
    public void handleGameNotFoundException(GameNotFoundException e) {
        LOGGER.error(e.getMessage());
        LOGGER.info(e.getCause().toString());
    }
}
