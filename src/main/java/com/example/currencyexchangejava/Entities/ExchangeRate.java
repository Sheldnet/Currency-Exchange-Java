package com.example.currencyexchangejava.Entities;

import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "ExchangeRates")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    Integer id;

    @JoinColumn(name = "BaseCurrencyId")
    Integer baseCurrencyId;

    @JoinColumn(name = "TargetCurrencyId")
    Integer targetCurrencyId;

    @Column(name = "Rate")
    BigDecimal rate;

}
