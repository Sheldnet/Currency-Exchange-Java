package com.example.currencyexchangejava.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@Table(name = "ExchangeRates")
public class ExchangeRate {

    @Id
    @Column(name = "Id")
    Integer id;

    @ManyToOne
    @JoinColumn(name = "BaseCurrencyId", referencedColumnName = "id")
    Currency baseCurrencyId;

    @ManyToOne
    @JoinColumn(name = "TargetCurrentId", referencedColumnName = "id")
    Currency targetCurrentId;

    @Column(name = "Rate")
    BigDecimal rate;

}
