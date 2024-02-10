package com.example.currencyexchangejava.Repositories;

import com.example.currencyexchangejava.Entities.Currency;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Integer> {

    @Query("SELECT * FROM Currencies")
    public List<Currency> getAll();
    @Query("SELECT * FROM Currencies WHERE ID = :id")
    public Currency getById(int id);
    @Query("SELECT * FROM Currencies WHERE Code = :code")
    public Currency getByCode(String code);
    @Modifying
    @Transactional
    @Query("INSERT INTO Currencies (Code, FullName, Sign) values (:code, :fullName, :sign)")
    public void insertCurrency (String code, String fullName, String sign);
}
