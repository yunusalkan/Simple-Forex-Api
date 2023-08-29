package com.yns.forex.simpleforexapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CurrencyList(boolean success, Map<String,String> currencies, Error error) {
}
