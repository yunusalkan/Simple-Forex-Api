package com.yns.forex.simpleforexapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.yns.forex.simpleforexapi.model.ConversionRequest;
import com.yns.forex.simpleforexapi.model.ConversionResponse;
import com.yns.forex.simpleforexapi.service.ConversionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ConversionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConversionService conversionService;

    @Test
    void shouldReturnExchangeRate() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.
                get("/api/v1/fx/pair/rate")
                .param("sourceCurrency", "USD")
                .param("targetCurrency", "TRY"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotReturnExchangeRateWhenWrongExchangeRate() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.
                        get("/api/v1/fx/pair/rate")
                        .param("sourceCurrency", "USD")
                        .param("targetCurrency", "TRL"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(10002))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("TRL currency type is not listed"));
    }

    @Test
    void shouldConvertCurrencyPair() throws Exception {

        ConversionRequest request = new ConversionRequest("USD", BigDecimal.valueOf(120), "TRY");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonValue = ow.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/fx")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonValue))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.conversionAmount").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionId").exists());
    }

    @Test
    void shouldGetConversionByTransactionId() throws Exception {

        ConversionRequest request = new ConversionRequest("USD", BigDecimal.valueOf(1000), "TRY");
        ConversionResponse conversionResponse = conversionService.convertCurrencyPair(request);

        Assertions.assertNotNull(conversionResponse);
        Assertions.assertNotNull(conversionResponse.transactionId());
        Assertions.assertNotNull(conversionResponse.conversionAmount());

        mockMvc.perform(MockMvcRequestBuilders.
                        get("/api/v1/fx/conversions")
                        .param("transactionId", conversionResponse.transactionId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));

    }

    @Test
    void shouldGetConversionListByTransactionDate() throws Exception {

        for (int i = 0; i < 10 ; i++) {
            ConversionRequest request = new ConversionRequest("USD", BigDecimal.valueOf(new Random().nextDouble()), "TRY");
            //Due to exchange api usage rate limits, requests are sent at 1 second intervals
            Thread.sleep(1000);
            ConversionResponse conversionResponse = conversionService.convertCurrencyPair(request);
        }

        mockMvc.perform(MockMvcRequestBuilders.
                        get("/api/v1/fx/conversions")
                        .param("transactionDate", LocalDate.now().toString())
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(10)));
    }

}
