package com.yns.forex.simpleforexapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ConversionListResponse(String transactionId,
                                     LocalDate transactionDate,
                                     String sourceCurrency,
                                     String targetCurrency,
                                     BigDecimal sourceAmount,
                                     BigDecimal targetAmount) {
}
