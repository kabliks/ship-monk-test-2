package com.shipmonk.testingday;

import com.shipmonk.testingday.client.fixer.FixerExchangeRatesClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableConfigurationProperties(FixerExchangeRatesClientConfig.class)
@PropertySource("classpath:application.yaml")
public class TestingdayExchangeRatesApplication {

    //TODO tune DB config

    public static void main(String[] args) {
        SpringApplication.run(TestingdayExchangeRatesApplication.class, args);
    }

}
