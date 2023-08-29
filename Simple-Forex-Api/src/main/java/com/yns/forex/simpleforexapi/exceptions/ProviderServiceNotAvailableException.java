package com.yns.forex.simpleforexapi.exceptions;

public class ProviderServiceNotAvailableException extends RuntimeException {

    public ProviderServiceNotAvailableException() {}

    public ProviderServiceNotAvailableException(String message) {
        super(message);
    }
}
