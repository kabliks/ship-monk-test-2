package com.shipmonk.testingday.service;

import java.time.LocalDate;

public interface ExchangeRatesService {

    /**
     * Get exchange rates for given day.
     * @param day Day to get exchange rates for.
     * @return Exchange rates for given day.
     * @throws com.shipmonk.testingday.client.ExchangeRatesClientException when underlying client call ends with error
     */
    ExchangeRatesDto getExchangeRates(LocalDate day);
}
