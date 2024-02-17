package com.example.currencyexchangejava.Entities;

import jakarta.persistence.*;
import lombok.*;

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
    private int id;

    @JoinColumn(name = "BaseCurrencyId")
    @ManyToOne
    private Currency baseCurrencyId;

    //@JoinColumn(name = "TargetCurrencyId")
    @ManyToOne
    private Currency targetCurrencyId;

    @Column(name = "Rate")
    private Double rate;

    public ExchangeRate(Currency baseCurrencyId, Currency targetCurrencyId, Double rate) {
        this.baseCurrencyId = baseCurrencyId;
        this.targetCurrencyId = targetCurrencyId;
        this.rate = rate;
    }
}
