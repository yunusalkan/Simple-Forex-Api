package com.yns.forex.simpleforexapi.model;

import java.math.BigDecimal;

public record ConversionResponse(BigDecimal conversionAmount, String transactionId) {
}
