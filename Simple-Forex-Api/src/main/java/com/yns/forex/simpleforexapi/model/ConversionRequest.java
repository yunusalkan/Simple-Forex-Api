package com.yns.forex.simpleforexapi.model;

import java.math.BigDecimal;

public record ConversionRequest(String sourceCurrency,
                                BigDecimal sourceAmount,
                                String targetCurrency) {
}
