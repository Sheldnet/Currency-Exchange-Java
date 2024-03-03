package com.example.currencyexchangejava.DTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ExchangeRateDTOResponse {
    private String baseCurrencyCode;
    private String targetCurrencyCode;
    private Double rate;
    private String amount;
}
