package com.example.currencyexchangejava.DTO;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ExchangeRateDTO {
    private String baseCurrencyCode;
    private String targetCurrencyCode;
    private Double rate;
    private String amount;
}
