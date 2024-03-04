package com.example.currencyexchangejava.Entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "exchangerates")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @JoinColumn(name = "basecurrencyid")
    @ManyToOne(fetch = FetchType.EAGER)
    private Currency baseCurrencyId;

    @JoinColumn(name = "targetcurrencyid")
    @ManyToOne(fetch = FetchType.EAGER)
    private Currency targetCurrencyId;

    @Column(name = "rate")
    private Double rate;

    public ExchangeRate(Currency baseCurrencyId, Currency targetCurrencyId, Double rate) {
        this.baseCurrencyId = baseCurrencyId;
        this.targetCurrencyId = targetCurrencyId;
        this.rate = rate;
    }
}
