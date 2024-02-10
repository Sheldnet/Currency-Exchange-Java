package com.example.currencyexchangejava.Entities;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExchangeRateRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setId(rs.getInt("id"));
        exchangeRate.setRate(rs.getBigDecimal("rate"));

        Currency baseCurrency = new Currency();
        baseCurrency.setId(rs.getInt("base_currency_id"));
        baseCurrency.setCode(rs.getString("base_currency_code"));
        baseCurrency.setFullName(rs.getString("base_currency_fullName"));
        baseCurrency.setSign(rs.getString("base_currency_sign"));
        exchangeRate.setBaseCurrencyId(baseCurrency);

        Currency targetCurrency = new Currency();
        targetCurrency.setId(rs.getInt("target_currency_id"));
        targetCurrency.setCode(rs.getString("target_currency_code"));
        targetCurrency.setFullName(rs.getString("target_currency_fullName"));
        targetCurrency.setSign(rs.getString("target_currency_sign"));
        exchangeRate.setBaseCurrencyId(targetCurrency);
        return exchangeRate;
    }
}
