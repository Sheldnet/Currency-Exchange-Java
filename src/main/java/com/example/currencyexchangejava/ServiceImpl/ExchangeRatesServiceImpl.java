package com.example.currencyexchangejava.ServiceImpl;

import com.example.currencyexchangejava.Entities.Currency;
import com.example.currencyexchangejava.Entities.ExchangeRate;
import com.example.currencyexchangejava.Repositories.ExchangeRateRepository;
import com.example.currencyexchangejava.Service.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

    private final ExchangeRateRepository exchangeRateRepository;
    @Autowired
    public ExchangeRatesServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }


    @Override
    public List<ExchangeRate> findAll() {
        return exchangeRateRepository.findAll();
    }

    @Override
    public ExchangeRate findExchangeRate(Currency base, Currency target) {
        return exchangeRateRepository.findByBaseCurrencyIdAndAndTargetCurrencyId(base,target);
    }

    @Override
    public ExchangeRate addExchangeRate(ExchangeRate exchangeRate) {
        return exchangeRateRepository.save(exchangeRate);
    }
}
