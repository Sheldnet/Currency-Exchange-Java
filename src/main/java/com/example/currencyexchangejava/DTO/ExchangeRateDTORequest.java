package com.example.currencyexchangejava.DTO;

import com.example.currencyexchangejava.Entities.Currency;
import lombok.*;

import java.math.BigDecimal;

@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDTORequest {
    private int id;
    private Currency baseCurrencyId;
    private Currency targetCurrencyId;
    private Double Rate;

    public ExchangeRateDTORequest(Currency baseCurrencyId, Currency targetCurrencyId) {
        this.baseCurrencyId = baseCurrencyId;
        this.targetCurrencyId = targetCurrencyId;
    }

    public ExchangeRateDTORequest(Currency baseCurrencyId, Currency targetCurrencyId, Double rate) {
        this.baseCurrencyId = baseCurrencyId;
        this.targetCurrencyId = targetCurrencyId;
        Rate = rate;
    }
}
