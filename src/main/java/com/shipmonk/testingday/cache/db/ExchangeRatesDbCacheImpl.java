package com.shipmonk.testingday.cache.db;

import com.shipmonk.testingday.cache.ExchangeRatesCache;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ExchangeRatesDbCacheImpl implements ExchangeRatesCache {

    //TODO save rates as json???

    private final ExchangeRateDbCacheRecordRepository repository;

    public ExchangeRatesDbCacheImpl(ExchangeRateDbCacheRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Map<String, BigDecimal>> getRates(LocalDate day, String baseCurrency) {
        List<ExchangeRateDbCacheRecord> records = repository.findAllByDayAndBaseCurrency(day, baseCurrency);
        if (records.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(records.stream().collect(Collectors.toMap(ExchangeRateDbCacheRecord::getCurrency, ExchangeRateDbCacheRecord::getRate)));
    }

    @Override
    @Transactional
    public void saveRates(LocalDate day, String baseCurrency, Map<String, BigDecimal> rates) {
        List<ExchangeRateDbCacheRecord> records = rates.entrySet().stream()
            .map(e -> new ExchangeRateDbCacheRecord(day, baseCurrency, e.getKey(), e.getValue()))
            .toList();
        repository.saveAll(records); //TODO create or replace
    }
}
