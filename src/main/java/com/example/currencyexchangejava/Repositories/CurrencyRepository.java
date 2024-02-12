package com.example.currencyexchangejava.Repositories;

import com.example.currencyexchangejava.Entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    public Currency findByCode(String code);
}
