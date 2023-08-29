package com.yns.forex.simpleforexapi.service.helper;

import com.yns.forex.simpleforexapi.exceptions.CurrencyTypeNotListedException;
import com.yns.forex.simpleforexapi.exceptions.ExchangeRateException;
import com.yns.forex.simpleforexapi.exceptions.ProviderServiceNotAvailableException;
import com.yns.forex.simpleforexapi.model.CurrencyList;
import com.yns.forex.simpleforexapi.model.ExchangeRates;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class CurrencyLayerFXService implements ExchangeProviderService {

    @Value("${fx.currency-layer.root-url}")
    private String rootUrl;

    @Value("${fx.currency-layer.list-url}")
    private String listUrl;

    private final RestTemplate restTemplate;
    private Map<String,String> availableCurrencyList;

    @PostConstruct
    private void postConstruct() {
        try {
            CurrencyList currencyList = restTemplate.getForObject(listUrl, CurrencyList.class);

            if (Objects.nonNull(currencyList) && currencyList.success()) {
                availableCurrencyList = currencyList.currencies();
            }
            else {
                throw new ProviderServiceNotAvailableException("Currency Layer FX Provider is not available !");
            }
        }
        catch (RestClientException exception) {
            throw new ProviderServiceNotAvailableException("Currency Layer FX Provider is not available !");
        }
    }

    @Override
    public BigDecimal getExchangeRate(String sourceCurrency, String targetCurrency) {
        if (!checkCurrencyTypeIsExist(sourceCurrency)) {
            throw new CurrencyTypeNotListedException(String.format("%s currency type is not listed", sourceCurrency));
        }

        if (!checkCurrencyTypeIsExist(targetCurrency)) {
            throw new CurrencyTypeNotListedException(String.format("%s currency type is not listed", targetCurrency));
        }

        final String EXCHANGE_RATE_URL = rootUrl.concat(String.format("&currencies=%s&source=%s&format=1", targetCurrency, sourceCurrency));
        try {
            ExchangeRates exchangeRates = restTemplate.getForObject(EXCHANGE_RATE_URL, ExchangeRates.class);
            if (!exchangeRates.success()) {
                throw new ExchangeRateException(exchangeRates.error().info());
            }
            Map<String, BigDecimal> rates = exchangeRates.quotes();
            return rates.get(sourceCurrency+targetCurrency);
        }
        catch (RestClientException exception) {
            throw new ExchangeRateException("Ann error occurred while getting exchange rate");
        }
    }

    private boolean checkCurrencyTypeIsExist(String currencyType) {
        return availableCurrencyList.containsKey(currencyType);
    }
}
