package com.yns.forex.simpleforexapi.configuration;

import com.yns.forex.simpleforexapi.exceptions.CurrencyTypeNotListedException;
import com.yns.forex.simpleforexapi.exceptions.ExchangeRateException;
import com.yns.forex.simpleforexapi.exceptions.InvalidInputException;
import com.yns.forex.simpleforexapi.exceptions.ProviderServiceNotAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    private static final int INVALID_INPUT = 10000;
    private static final int PROVIDER_SERVICE_UNAVAILABLE = 10001;
    private static final int CURRENCY_TYPE_NOT_LISTED = 10002;
    private static final int EXCHANGE_RATE_ERROR = 10003;

    @ExceptionHandler(ProviderServiceNotAvailableException.class)
    public ResponseEntity<Object> handleProviderServiceNotAvailableException(ProviderServiceNotAvailableException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("errorCode", PROVIDER_SERVICE_UNAVAILABLE);
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(CurrencyTypeNotListedException.class)
    public ResponseEntity<Object> handleCurrencyTypeNotListedException(CurrencyTypeNotListedException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("errorCode", CURRENCY_TYPE_NOT_LISTED);
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExchangeRateException.class)
    public ResponseEntity<Object> handleExchangeRateException(ExchangeRateException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("errorCode", EXCHANGE_RATE_ERROR);
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Object> handleInvalidInputException(InvalidInputException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("errorCode", INVALID_INPUT);
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


}
