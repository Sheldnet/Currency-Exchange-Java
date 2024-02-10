package com.example.currencyexchangejava.ServiceImpl;

import com.example.currencyexchangejava.Entities.ExchangeRate;
import com.example.currencyexchangejava.Repositories.ExchangeRateRepository;
import com.example.currencyexchangejava.Service.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {
    @Autowired
    ExchangeRateRepository exchangeRateRepository;
    @Override
    public Optional<List<ExchangeRate>> getAll() {
        List<ExchangeRate> exchangeRates = exchangeRateRepository.findAll();
        return Optional.ofNullable(exchangeRates);
    }

    @Override
    public Optional<ExchangeRate> getExchangeRateByCodes(String baseCurrencyCode, String targetCurrencyCode) {
        ExchangeRate exchangeRate = exchangeRateRepository.findByCodes(baseCurrencyCode.toUpperCase(),targetCurrencyCode.toUpperCase());
        return Optional.ofNullable(exchangeRate);
    }

    @Override
    public Optional<Integer> deleteExchangeRate(Integer id) {
        return Optional.ofNullable(exchangeRateRepository.deleteExchangeRateById(id) ? id : null);
    }

    @Override
    public Optional<ExchangeRate> insertExchangeRate(Integer base, Integer target, BigDecimal rate) {
        exchangeRateRepository.insertExchangeRate(base,target,rate);
        ExchangeRate exchangeRate = exchangeRateRepository.findByCodes(base.toString().toUpperCase(),target.toString().toUpperCase());
        return Optional.ofNullable(exchangeRate);
    }
}
