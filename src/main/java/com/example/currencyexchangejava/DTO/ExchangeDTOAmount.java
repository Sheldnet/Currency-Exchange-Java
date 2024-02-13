package com.example.currencyexchangejava.DTO;

import com.example.currencyexchangejava.Entities.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeDTOAmount {
    private Currency base;
    private Currency target;
    private BigDecimal rate;
    private String amount;
    private double convertedAmount;
}
