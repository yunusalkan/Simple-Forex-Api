package com.yns.forex.simpleforexapi.configuration;

import com.yns.forex.simpleforexapi.service.helper.CurrencyLayerFXService;
import com.yns.forex.simpleforexapi.service.helper.ExchangeProviderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfig {

    @Bean
    public ExchangeProviderService currencyLayerFXService() {
        return new CurrencyLayerFXService(new RestTemplate());
    }
}
