package com.example.currencyexchangejava.Service;

import com.example.currencyexchangejava.Entities.Currency;

import javax.swing.*;

import java.util.List;
import java.util.Optional;

public interface CurrinciesService {

    Optional<List<Currency>> getAll();
    Optional<Currency> getById(int id);

    Optional<Currency> getByCode(String code);
    Optional<Integer> insertCurrency(String code, String fullName, String sign);

}
