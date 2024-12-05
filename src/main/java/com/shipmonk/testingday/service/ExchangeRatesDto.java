package com.shipmonk.testingday.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public record ExchangeRatesDto(
    LocalDate day,
    String baseCurrency,
    Map<String, BigDecimal> rates) {
}
