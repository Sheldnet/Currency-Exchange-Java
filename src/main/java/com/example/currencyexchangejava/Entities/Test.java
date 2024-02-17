package com.example.currencyexchangejava.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "test")
@Data
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int test;
    private String name;
}
