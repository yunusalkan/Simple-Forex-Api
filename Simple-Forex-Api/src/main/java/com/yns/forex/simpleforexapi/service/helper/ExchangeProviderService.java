package com.yns.forex.simpleforexapi.service.helper;

import java.math.BigDecimal;

public interface ExchangeProviderService {

    public BigDecimal getExchangeRate(String sourceCurrency, String targetCurrency);
}
