package com.example.currencyexchangejava.ServiceImpl;

import com.example.currencyexchangejava.Entities.Currency;
import com.example.currencyexchangejava.Repositories.CurrencyRepository;
import com.example.currencyexchangejava.Service.CurrenciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class CurrenciesServiceImpl implements CurrenciesService {


    private final CurrencyRepository currencyRepository;
    @Autowired
    public CurrenciesServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }


    @Override
    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    @Override
    public Currency findById(int id) {
        Currency currency = null;
        Optional<Currency> optional = currencyRepository.findById(id);
        if (optional.isPresent()) {
            currency = optional.get();
        }
        return currency;
    }

    @Override
    public Currency findByCode(String code) {
        return currencyRepository.findByCode(code);
    }

    @Override
    public Currency addCurrencies(Currency currency) {
        return currencyRepository.save(currency);
    }
}
