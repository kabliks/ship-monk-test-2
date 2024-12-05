package com.shipmonk.testingday.service;

import com.shipmonk.testingday.cache.ExchangeRatesCache;
import com.shipmonk.testingday.client.ExchangeRatesClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ExchangeRatesServiceImplTest {

    //TODO more tests

    private static final LocalDate DAY = LocalDate.of(2024, 10, 12);
    private static final String BASE_CURRENCY = "USD";
    private static final Map<String, BigDecimal> RATES = Map.of("EUR", BigDecimal.ONE, "CZK", BigDecimal.TEN);

    @Mock
    private ExchangeRatesCache cache;

    @Mock
    private ExchangeRatesClient client;

    @InjectMocks
    private ExchangeRatesServiceImpl service;

    private AutoCloseable openMocks;

    @BeforeEach
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(service, "baseCurrency", BASE_CURRENCY);
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    public void testLoadFromCache() {
        when(cache.getRates(DAY, BASE_CURRENCY)).thenReturn(Optional.of(RATES));

        ExchangeRatesDto exchangeRates = service.getExchangeRates(DAY);
        assertEquals(RATES, exchangeRates.rates());

        verify(cache, times(1)).getRates(DAY, BASE_CURRENCY);
        verifyNoMoreInteractions(cache);

        verifyNoMoreInteractions(client);
    }

    @Test
    public void testLoadFromClient() {
        when(cache.getRates(DAY, BASE_CURRENCY)).thenReturn(Optional.empty());
        when(client.getForDay(DAY, BASE_CURRENCY)).thenReturn(RATES);

        ExchangeRatesDto exchangeRates = service.getExchangeRates(DAY);
        assertEquals(RATES, exchangeRates.rates());

        verify(cache, times(1)).getRates(DAY, BASE_CURRENCY);
        verify(cache, times(1)).saveRates(DAY, BASE_CURRENCY, RATES);
        verifyNoMoreInteractions(cache);

        verify(client, times(1)).getForDay(DAY, BASE_CURRENCY);
        verifyNoMoreInteractions(client);
    }
}
