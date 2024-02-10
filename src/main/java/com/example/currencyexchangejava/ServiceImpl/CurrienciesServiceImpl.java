package com.example.currencyexchangejava.ServiceImpl;

import com.example.currencyexchangejava.Entities.Currency;
import com.example.currencyexchangejava.Repositories.CurrencyRepository;
import com.example.currencyexchangejava.Service.CurrinciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
@Service
public class CurrienciesServiceImpl implements CurrinciesService {

    @Autowired
    CurrencyRepository currencyRepository;
    @Override
    public Optional<List<Currency>> getAll() {
        List<Currency> currencies = currencyRepository.getAll();
        return Optional.ofNullable(currencies);
    }

    @Override
    public Optional<Currency> getById(int id) {
        Currency currency = currencyRepository.getById(id);
        return Optional.ofNullable(currency);
    }

    @Override
    public Optional<Currency> getByCode(String code) {
        Currency currency = currencyRepository.getByCode(code.toUpperCase());
        return Optional.ofNullable(currency);
    }

    @Override
    public Optional<Integer> insertCurrency(String code, String fullName, String sign) {
        currencyRepository.insertCurrency(code.toUpperCase(),fullName,sign);
        Currency currency = currencyRepository.getByCode(code.toUpperCase());
        return Optional.ofNullable(currency.getId());
    }
}
