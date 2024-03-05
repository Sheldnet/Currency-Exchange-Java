package com.example.currencyexchangejava.Service;

import com.example.currencyexchangejava.Entities.Currency;
import com.example.currencyexchangejava.Entities.ExchangeRate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExchangeRatesService {
    public List<ExchangeRate> findAll();
    public ExchangeRate findExchangeRate(Currency base, Currency target);

    public ExchangeRate addExchangeRate(ExchangeRate exchangeRate);

    public void updateExchangeRate(ExchangeRate exchangeRate);

}
