package com.example.currencyexchangejava.Repositories;

import com.example.currencyexchangejava.Entities.ExchangeRate;
import com.example.currencyexchangejava.Entities.ExchangeRateRowMapper;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Integer> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO  ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (:baseCurrencyId,:targetCurrencyId,:rate)")
    public void insertExchangeRate (Integer baseCurrencyId, Integer targetCurrencyId, BigDecimal rate);

    @Modifying
    @Query("DELETE FROM ExchangeRates WHERE ID = :id")
    public boolean deleteExchangeRateById(Integer id);

    @Query(value = "SELECT er.id as id, er.Rate as rate," +
            "bc.ID as base_currency_id, bc.Code as base_currency_code, bc.FullName as base_currency_fullName, bc.Sign as base_currency_sign, " +
            "tc.ID as target_currency_id, tc.Code as target_currency_code, tc.FullName as target_currency_fullName, tc.Sign as target_currency_sign" +
            " FROM ExchangeRates er " +
            "INNER JOIN Currencies bc ON er.BaseCurrencyId = bc.ID " +
            "INNER JOIN Currencies tc on tc.ID = er.TargetCurrencyId ",
            rowMapperClass = ExchangeRateRowMapper.class)
    public List<ExchangeRate> findAll();

    @Query(value = "SELECT er.id as id, er.Rate as rate," +
            " bc.ID as base_currency_id, bc.Code as base_currency_code, bc.FullName as base_currency_fullName, bc.Sign as base_currency_sign," +
            " tc.ID as target_currency_id, tc.Code as target_currency_code, tc.FullName as target_currency_fullName, tc.Sign as target_currency_sign" +
            " FROM ExchangeRates er " +
            " INNER JOIN Currencies bc ON er.BaseCurrencyId = bc.ID " +
            " INNER JOIN Currencies tc on tc.ID = er.TargetCurrencyId " +
            " WHERE bc.Code = :baseCurrencyCode AND tc.Code = :targetCurrencyCode "
    , rowMapperClass = ExchangeRateRowMapper.class)
    public ExchangeRate findByCodes(String baseCurrencyCode, String targetCurrencyCode);
}
