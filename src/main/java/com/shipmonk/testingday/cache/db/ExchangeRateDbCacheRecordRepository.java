package com.shipmonk.testingday.cache.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeRateDbCacheRecordRepository extends JpaRepository<ExchangeRateDbCacheRecord, Long> {

    List<ExchangeRateDbCacheRecord> findAllByDayAndBaseCurrency(LocalDate day, String baseCurrency);
}
