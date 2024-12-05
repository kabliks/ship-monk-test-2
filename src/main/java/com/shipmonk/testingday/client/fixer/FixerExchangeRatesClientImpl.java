package com.shipmonk.testingday.client.fixer;

import com.shipmonk.testingday.client.ExchangeRatesClient;
import com.shipmonk.testingday.client.ExchangeRatesClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Component
public class FixerExchangeRatesClientImpl implements ExchangeRatesClient {

    private static final Logger LOG = LoggerFactory.getLogger(FixerExchangeRatesClientImpl.class);

    private static final String API_KEY_QUERY_PARAM = "access_key";
    private static final String BASE_CURRENCY_QUERY_PARAM = "base";

    private final RestClient restClient;
    private final String apiKey;

    public FixerExchangeRatesClientImpl(FixerExchangeRatesClientConfig config) {
        apiKey = config.apiKey();
        restClient = RestClient.builder()
            .baseUrl(config.baseUri())
            .build();
    }

    @Override
    public Map<String, BigDecimal> getForDay(LocalDate day, String baseCurrency) {
        FixerExchangeRatesResponse response = null;
        try {
            response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/{day}")
                    .queryParam(API_KEY_QUERY_PARAM, apiKey)
                    .queryParam(BASE_CURRENCY_QUERY_PARAM, baseCurrency)
                    .build(day))
                .retrieve()
                .body(FixerExchangeRatesResponse.class);
        } catch (RestClientException e) {
            processRestException(e);
        }

        if (!response.isSuccess()) {
            processError(response.getError());
        }

        return response.getRates();
    }

    private void processRestException(RestClientException e) {
        LOG.error("Fixer API call error: ", e);
        throw new ExchangeRatesClientException("Fixer API call error: " + e.getMessage());
    }

    private void processError(FixerExchangeRatesResponse.Error error) {
        LOG.error("Fixer API returns error. Error code: {}, error type: {}", error.getCode(), error.getType());
        throw new ExchangeRatesClientException("Fixer API returns error code " + error.getCode() + ": " + error.getType());
    }
}
