package com.yns.forex.simpleforexapi.exceptions;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException() {
    }

    public InvalidInputException(String message) {
        super(message);
    }
}
