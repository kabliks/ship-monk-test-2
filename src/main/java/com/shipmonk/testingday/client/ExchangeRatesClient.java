package com.shipmonk.testingday.client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public interface ExchangeRatesClient {

    /**
     * Get exchange rates for given day and base currency.
     * @param day Day to get rates fore.
     * @param baseCurrency Currency that rates are related to.
     * @return Exchange rates
     * @throws ExchangeRatesClientException in case of error
     */
    Map<String, BigDecimal> getForDay(LocalDate day, String baseCurrency);
}
