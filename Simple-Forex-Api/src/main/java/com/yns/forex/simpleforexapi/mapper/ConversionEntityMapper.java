package com.yns.forex.simpleforexapi.mapper;

import com.yns.forex.simpleforexapi.model.ConversionListResponse;
import com.yns.forex.simpleforexapi.persistence.ConversionEntity;

import java.util.Objects;

public class ConversionEntityMapper {

    public static ConversionListResponse mapToConversionResponse(ConversionEntity entity) {
        if (Objects.nonNull(entity)) {
            return new ConversionListResponse(
                    entity.getTransactionId(),
                    entity.getTransactionDate(),
                    entity.getSourceCurrency(),
                    entity.getTargetCurrency(),
                    entity.getSourceAmount(),
                    entity.getTargetAmount());
        }
        else {
            return null;
        }
    }
}
