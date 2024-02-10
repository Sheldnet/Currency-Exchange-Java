package com.example.currencyexchangejava.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Currencies")
public class Currency {
    public Currency(String code, String fullName) {
        this.code = code;
        this.fullName = fullName;
    }

    public Currency(String code, String fullName, String sign) {
        this.code = code;
        this.fullName = fullName;
        this.sign = sign;
    }

    @Id
    @Column(name = "Id")
    Integer id;
    @Column(name = "Code")
    String code;
    @Column(name = "FullName")
    String fullName;
    @Column(name = "Sign")
    String sign;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;
        Currency currency = (Currency) o;
        return  Objects.equals(this.code, currency.code) &&
                Objects.equals(this.fullName, currency.fullName);
    }

}
