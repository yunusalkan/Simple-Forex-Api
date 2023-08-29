package com.yns.forex.simpleforexapi.service;

import com.yns.forex.simpleforexapi.exceptions.InvalidInputException;
import com.yns.forex.simpleforexapi.mapper.ConversionEntityMapper;
import com.yns.forex.simpleforexapi.model.ConversionListResponse;
import com.yns.forex.simpleforexapi.model.ConversionRequest;
import com.yns.forex.simpleforexapi.model.ConversionResponse;
import com.yns.forex.simpleforexapi.persistence.ConversionEntity;
import com.yns.forex.simpleforexapi.persistence.ConversionRepository;
import com.yns.forex.simpleforexapi.service.helper.ExchangeProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConversionService {

    private final ExchangeProviderService currencyLayerFXService;

    private final ConversionRepository repository;

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public BigDecimal getExchangeRateOfPair(String sourceCurrency, String targetCurrency) {
        return currencyLayerFXService.getExchangeRate(sourceCurrency, targetCurrency);
    }

    public ConversionResponse convertCurrencyPair(ConversionRequest request) {
        String transactionId = UUID.randomUUID().toString();
        BigDecimal exchangeRate = currencyLayerFXService.getExchangeRate(request.sourceCurrency(), request.targetCurrency());
        BigDecimal sourceAmount = request.sourceAmount().setScale(2, RoundingMode.HALF_UP);
        BigDecimal conversionAmount = sourceAmount.multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);


        ConversionEntity conversionEntity = ConversionEntity.builder().sourceAmount(request.sourceAmount())
                .targetAmount(conversionAmount)
                .targetCurrency(request.targetCurrency())
                .transactionId(transactionId)
                .sourceCurrency(request.sourceCurrency())
                .transactionDate(LocalDate.now())
                .sourceAmount(sourceAmount)
                .build();

        repository.save(conversionEntity);

        return new ConversionResponse(conversionEntity.getTargetAmount(), transactionId);
    }

    public List<ConversionListResponse> getConversionListByParameters(String transactionId, LocalDate transactionDate,
                                                                      int page, int size) {
        if (Objects.isNull(transactionId) && Objects.isNull(transactionDate)) {
            throw new InvalidInputException("transactionId or transactionDate parameter must be filled!");
        }

        if (Objects.nonNull(transactionId)) {
            ConversionListResponse response = ConversionEntityMapper.mapToConversionResponse(repository.findByTransactionId(transactionId));
            return Objects.nonNull(response) ? List.of(response) : List.of();
        } else {
            Pageable paging = PageRequest.of(page, size);
            List<ConversionEntity> allByTransactionDate = repository.findAllByTransactionDate(transactionDate, paging);
            if (!CollectionUtils.isEmpty(allByTransactionDate)) {
                return allByTransactionDate.stream().map(ConversionEntityMapper::mapToConversionResponse)
                        .collect(Collectors.toList());
            } else {
                return List.of();
            }
        }
    }

}
