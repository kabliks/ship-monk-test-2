package com.shipmonk.testingday.cache;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public interface ExchangeRatesCache {

    /**
     * Get cached rates for given day and base currency
     * @param day Day that rates are for.
     * @param baseCurrency Currency that rates are related to.
     * @return Exchange rates or empty {@link Optional} when no records found.
     */
    Optional<Map<String, BigDecimal>> getRates(LocalDate day, String baseCurrency);

    /**
     * Save rates to cache.
     * @param day Day that rates are for.
     * @param baseCurrency Currency that rates are related to.
     * @param rates Exchange rates to be saved.
     */
    void saveRates(LocalDate day, String baseCurrency, Map<String, BigDecimal> rates);
}
