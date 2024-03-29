package com.example.currencyexchangejava.Repositories;

import com.example.currencyexchangejava.Entities.Currency;
import com.example.currencyexchangejava.Entities.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {
   ExchangeRate findByBaseCurrencyIdAndAndTargetCurrencyId(Currency base, Currency target);

}
