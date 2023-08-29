package com.yns.forex.simpleforexapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExchangeRates(boolean success,String source, Map<String, BigDecimal> quotes, Error error) {
}
