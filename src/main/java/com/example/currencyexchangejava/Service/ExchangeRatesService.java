package com.example.currencyexchangejava.Service;

import com.example.currencyexchangejava.Entities.ExchangeRate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExchangeRatesService {
    public List<ExchangeRate> findAll();
    public ExchangeRate findExchangeRate(int base, int target);

    public ExchangeRate addExchangeRate(ExchangeRate exchangeRate);

}
