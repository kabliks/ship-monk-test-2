package com.shipmonk.testingday.controller;

import com.shipmonk.testingday.client.ExchangeRatesClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = ExchangeRatesController.class)
public class ExchangeRatesControllerAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(ExchangeRatesControllerAdvice.class);

    @ExceptionHandler(ExchangeRatesClientException.class)
    public ResponseEntity<String> handleClientException(ExchangeRatesClientException exception) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(exception.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException exception) {
        LOG.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }
}
