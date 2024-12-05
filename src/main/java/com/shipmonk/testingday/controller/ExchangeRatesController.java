package com.shipmonk.testingday.controller;

import com.shipmonk.testingday.service.ExchangeRatesDto;
import com.shipmonk.testingday.service.ExchangeRatesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping(
    path = "/api/v1/rates"
)
public class ExchangeRatesController {

    private final ExchangeRatesService exchangeRatesService;

    public ExchangeRatesController(ExchangeRatesService exchangeRatesService) {
        this.exchangeRatesService = exchangeRatesService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{day}")
    public ResponseEntity<ExchangeRatesDto> getRates(@PathVariable("day") LocalDate day) {
        if (LocalDate.now().isBefore(day)) {
            throw new ValidationException("Day must be today or earlier.");
        }

        ExchangeRatesDto exchangeRates = exchangeRatesService.getExchangeRates(day);
        //TODO map exchangeRates to response entity (for example generated from OpenAPI specification)
        return ResponseEntity.ok(exchangeRates);
    }

}
