package com.example.currencyexchangejava.Controllers;

import com.example.currencyexchangejava.Service.CurrenciesService;
import com.example.currencyexchangejava.Service.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

    private final CurrenciesService currenciesService;
    private final ExchangeRatesService exchangeRatesService;
    @Autowired
    public CurrencyExchangeController(CurrenciesService currenciesService, ExchangeRatesService exchangeRatesService) {
        this.currenciesService = currenciesService;
        this.exchangeRatesService = exchangeRatesService;
    }

    @RequestMapping("/isalive")
    String isAlive(){
        return "server is alive";
    }

}
