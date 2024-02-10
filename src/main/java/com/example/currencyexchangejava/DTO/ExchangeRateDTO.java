package com.example.currencyexchangejava.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeRateDTO {
    private String baseCurrencyCode;
    private String targetCurrencyCode;
    private BigDecimal rate;
}
