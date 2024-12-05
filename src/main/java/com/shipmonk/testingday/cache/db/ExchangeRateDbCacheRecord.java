package com.shipmonk.testingday.cache.db;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "EXCHANGE_RATE_CACHE_RECORD")
public class ExchangeRateDbCacheRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "DAY", nullable = false)
    private LocalDate day;

    @Column(name = "BASE_CURRENCY", nullable = false, length = 3)
    private String baseCurrency;

    @Column(name = "CURRENCY", nullable = false, length = 3)
    private String currency;

    @Column(name = "RATE", nullable = false)
    private BigDecimal rate;

    public ExchangeRateDbCacheRecord() {
    }

    public ExchangeRateDbCacheRecord(LocalDate day, String baseCurrency, String currency, BigDecimal rate) {
        this.day = day;
        this.baseCurrency = baseCurrency;
        this.currency = currency;
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
