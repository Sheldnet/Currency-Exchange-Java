package com.example.currencyexchangejava.Repositories;

import com.example.currencyexchangejava.Entities.Currency;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Integer> {

    @Query("SELECT * FROM Currencies")
    public List<Currency> getAll();

    public Currency getById();

    public Currency getByCode();

    public void insertCurrency (String code, String fullName, String sign);
}
