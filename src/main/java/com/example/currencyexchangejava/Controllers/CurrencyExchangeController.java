package com.example.currencyexchangejava.Controllers;

import com.example.currencyexchangejava.Service.CurrinciesService;
import com.example.currencyexchangejava.Service.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
    @Autowired
    CurrinciesService currinciesService;
    @Autowired
    ExchangeRatesService exchangeRatesService;



}
