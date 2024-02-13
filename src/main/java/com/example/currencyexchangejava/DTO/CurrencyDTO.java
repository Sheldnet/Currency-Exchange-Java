package com.example.currencyexchangejava.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CurrencyDTO {
    private String code;
    private String fullName;
    private String sign;
}
