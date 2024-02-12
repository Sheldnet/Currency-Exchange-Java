package com.example.currencyexchangejava.Service;

import com.example.currencyexchangejava.Entities.Currency;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CurrenciesService {

    List<Currency> findAll();

    public Currency findById(int id);

    public Currency findByCode(String code);
    public Currency addCurrencies(Currency currency);
}
