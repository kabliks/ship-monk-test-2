package com.shipmonk.testingday.client.fixer;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "client.fixer")
public record FixerExchangeRatesClientConfig(String baseUri, String apiKey) {
}

