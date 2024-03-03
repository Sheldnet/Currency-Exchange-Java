package com.example.currencyexchangejava.Controllers;

import com.example.currencyexchangejava.DTO.CurrencyDTO;
import com.example.currencyexchangejava.Entities.Currency;
import com.example.currencyexchangejava.Entities.ExchangeRate;
import com.example.currencyexchangejava.Service.CurrenciesService;
import com.example.currencyexchangejava.Service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
public class CurrencyController {

    private final CurrenciesService currenciesService;
    private final FactoryService factoryService;
    @Autowired
    public CurrencyController(CurrenciesService currenciesService, FactoryService factoryService) {
        this.currenciesService = currenciesService;
        this.factoryService = factoryService;
    }


    @RequestMapping("/isalive")
    String isAlive(){
        return "server is alive";
    }

    @GetMapping("/сurrencies")
    public ResponseEntity<List<Currency>> getCurrencies() throws Exception {
        try {
            return new ResponseEntity<>(currenciesService.findAll(), HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new Exception("ошибка бд");
        }
    }

    @GetMapping("/currency/{currency}")
    public ResponseEntity<Currency> getCurrencyByCode(@PathVariable("currency") String code) throws Exception {
        if (code.isBlank()) {
            throw new Exception("код валюты отсутствует");
        }
        try {
            if (currenciesService.findByCode(code) == null) {
                throw new Exception("валюта" + code + "не найдена");
            }
            return  new ResponseEntity<>(currenciesService.findByCode(code), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception("Не удаётся подключится к бд");
        }
    }

    //TODO: Переделать логику добавления currency, вообще не понял зачем тут ДТО
    @PostMapping("/currencies")
    public ResponseEntity<Currency> addCurrency(CurrencyDTO currencyDTO) throws Exception {
        if (currencyDTO.getCode().isEmpty()
                || currencyDTO.getFullName().isEmpty()
        || currencyDTO.getSign().isEmpty()) {
            throw new Exception("отсутствует нужное поле формы");
        }
        try {
            return new ResponseEntity<>(currenciesService.addCurrencies(factoryService.convertCurrencyDTOToCurrency(currencyDTO)), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception("валюты нет или что-то с подключением");
        }
    }


}
