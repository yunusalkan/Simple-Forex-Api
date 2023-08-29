package com.yns.forex.simpleforexapi.exceptions;

public class CurrencyTypeNotListedException extends RuntimeException {

    public CurrencyTypeNotListedException() {
    }

    public CurrencyTypeNotListedException(String message) {
        super(message);
    }
}
