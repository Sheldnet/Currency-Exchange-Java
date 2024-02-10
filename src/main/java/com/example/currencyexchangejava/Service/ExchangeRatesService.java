package com.example.currencyexchangejava.Service;

import com.example.currencyexchangejava.Entities.ExchangeRate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ExchangeRatesService {
    Optional<List<ExchangeRate>> getAll();

    Optional<ExchangeRate> getExchangeRateByCodes(String baseCurrencyCode, String targetCurrencyCode);

    Optional<Integer> deleteExchangeRate(Integer id);

    Optional<ExchangeRate> insertExchangeRate(Integer base, Integer target, BigDecimal rate);
}
