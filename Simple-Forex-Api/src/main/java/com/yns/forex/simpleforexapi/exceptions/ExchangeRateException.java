package com.yns.forex.simpleforexapi.exceptions;

public class ExchangeRateException extends RuntimeException {

    public ExchangeRateException() {}

    public ExchangeRateException(String message) {
        super(message);
    }
}
