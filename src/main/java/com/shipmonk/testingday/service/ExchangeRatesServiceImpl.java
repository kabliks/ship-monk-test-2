package com.shipmonk.testingday.service;

import com.shipmonk.testingday.cache.ExchangeRatesCache;
import com.shipmonk.testingday.client.ExchangeRatesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

    private static final Logger LOG = LoggerFactory.getLogger(ExchangeRatesServiceImpl.class);

    private final ExchangeRatesClient exchangeRatesClient;
    private final ExchangeRatesCache exchangeRatesCache;

    private final String baseCurrency;

    public ExchangeRatesServiceImpl(ExchangeRatesClient exchangeRatesClient, ExchangeRatesCache exchangeRatesCache, @Value("${application.base-currency:USD}") String baseCurrency) {
        this.exchangeRatesClient = exchangeRatesClient;
        this.exchangeRatesCache = exchangeRatesCache;
        this.baseCurrency = baseCurrency;
    }

    @Override
    public ExchangeRatesDto getExchangeRates(LocalDate day) {
        Map<String, BigDecimal> rates = getRatesFromCache(day);
        if (rates == null) {
            LOG.debug("No cache record found for day {} and base currency {}. Calling client to get rates.", day, baseCurrency);
            rates = exchangeRatesClient.getForDay(day, baseCurrency);
            saveRatesToCache(day, rates);
        }
        return new ExchangeRatesDto(day, baseCurrency, rates);
    }

    private Map<String, BigDecimal> getRatesFromCache(LocalDate day) {
        try { //ignore cache error
            return exchangeRatesCache.getRates(day, baseCurrency).orElse(null);
        } catch (RuntimeException e) {
            LOG.error("Cache read error: ", e);
            return null;
        }
    }

    private void saveRatesToCache(LocalDate day, Map<String, BigDecimal> rates) {
        try { //ignore cache error
            exchangeRatesCache.saveRates(day, baseCurrency, rates);
        } catch (RuntimeException e) {
            LOG.error("Cache write error: ", e);
        }
    }
}
