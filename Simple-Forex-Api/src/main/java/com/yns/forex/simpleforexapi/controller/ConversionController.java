package com.yns.forex.simpleforexapi.controller;


import com.yns.forex.simpleforexapi.model.ConversionListResponse;
import com.yns.forex.simpleforexapi.model.ConversionRequest;
import com.yns.forex.simpleforexapi.model.ConversionResponse;
import com.yns.forex.simpleforexapi.service.ConversionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "ConversionController", description = "REST API for ConversionController")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/fx", produces = APPLICATION_JSON_VALUE)
public class ConversionController {

    private final ConversionService conversionService;

    @Operation(
            summary = "${api.conversion-controller.get-exchange-rate.description}",
            description = "${api.conversion-controller.get-exchange-rate.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "10002", description = "${api.response-codes.wrong-currency-type}"),
            @ApiResponse(responseCode = "10003", description = "${api.response-codes.exchange-service-exception}")
    })
    @GetMapping("/pair/rate")
    public ResponseEntity<BigDecimal> getExchangeRate(@RequestParam String sourceCurrency,
                                                      @RequestParam String targetCurrency) {
        return ResponseEntity.ok(conversionService.getExchangeRateOfPair(sourceCurrency, targetCurrency));
    }

    @Operation(
            summary = "${api.conversion-controller.convert-currency-pair.description}",
            description = "${api.conversion-controller.convert-currency-pair.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "10002", description = "${api.response-codes.wrong-currency-type}"),
            @ApiResponse(responseCode = "10003", description = "${api.response-codes.exchange-service-exception}")
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ConversionResponse> convertCurrencyPair(@RequestBody ConversionRequest request) {
        return ResponseEntity.ok(conversionService.convertCurrencyPair(request));
    }

    @Operation(
            summary = "${api.conversion-controller.get-conversion-list.description}",
            description = "${api.conversion-controller.get-conversion-list.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "10002", description = "${api.response-codes.wrong-currency-type}"),
            @ApiResponse(responseCode = "10003", description = "${api.response-codes.exchange-service-exception}"),
            @ApiResponse(responseCode = "10000", description = "${api.response-codes.invalid-input-exception}")
    })
    @GetMapping("/conversions")
    public ResponseEntity<List<ConversionListResponse>> getConversionList(@RequestParam(required = false) String transactionId,
                                                                          @RequestParam(required = false)
                                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate transactionDate,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(conversionService.getConversionListByParameters(transactionId, transactionDate, page, size));
    }
}
