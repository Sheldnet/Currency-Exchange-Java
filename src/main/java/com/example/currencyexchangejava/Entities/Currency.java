package com.example.currencyexchangejava.Entities;



import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "Currencies")
@ToString
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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "Code")
    private String code;
    @Column(name = "FullName")
    private String fullName;
    @Column(name = "Sign")
    private String sign;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;
        Currency currency = (Currency) o;
        return  Objects.equals(this.code, currency.code) &&
                Objects.equals(this.fullName, currency.fullName);
    }

}
