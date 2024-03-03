package com.example.currencyexchangejava.Controllers;

import com.example.currencyexchangejava.Entities.Currency;
import com.example.currencyexchangejava.Entities.ExchangeRate;
import com.example.currencyexchangejava.DTO.ExchangeDTOAmount;
import com.example.currencyexchangejava.DTO.ExchangeRateDTOResponse;
import com.example.currencyexchangejava.DTO.ExchangeRateDTORequest;
import com.example.currencyexchangejava.Service.CurrenciesService;
import com.example.currencyexchangejava.Service.ExchangeRatesService;
import com.example.currencyexchangejava.Service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExchangeController {
    private final CurrenciesService currenciesService;

    private final ExchangeRatesService exchangeRatesService;
    private final FactoryService factoryService;
    @Autowired
    public ExchangeController(CurrenciesService currenciesService, ExchangeRatesService exchangeRatesService, FactoryService factoryService) {
        this.currenciesService = currenciesService;
        this.exchangeRatesService = exchangeRatesService;
        this.factoryService = factoryService;
    }


    @GetMapping("/exchangeRates")
    public ResponseEntity<List<ExchangeRateDTORequest>> getExchangeRates() throws Exception {
        try {
            return new ResponseEntity<>(factoryService.getExchangeRateDTORequest(exchangeRatesService.findAll()), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception("ошибка подключения бд");
        }
    }

    @GetMapping("/exchangeRate/{exchangeRate}")
    public ResponseEntity<ExchangeRateDTORequest> getExchangeRate(@PathVariable("exchangeRate") String code) throws Exception {
        if (code.isBlank()) {
            throw new Exception("Не введены данные");
        }
        try {
            Currency baseCurrency = currenciesService.findByCode(code);
            Currency targetCurrency = currenciesService.findByCode(code);
            ExchangeRate exchangeRate = exchangeRatesService.findExchangeRate(baseCurrency, targetCurrency);

            if (exchangeRate != null) {
                return new ResponseEntity<>(factoryService.converterExchangeRateIntoExchangeRateDTO(exchangeRate), HttpStatus.OK);
            } else {
                throw new Exception("Курс обмена не найден для валюты с кодом " + code);
            }
        } catch (DataAccessException e) {
            throw new Exception("Не удаётся подключиться к базе данных");
        }
    }


    @PostMapping("/exchangeRates")
    public ResponseEntity<ExchangeRateDTORequest> addExchangeRate(ExchangeRateDTOResponse exchangeRateDTOResponse) throws Exception {
        if (exchangeRateDTOResponse.getBaseCurrencyCode().isBlank() ||
                exchangeRateDTOResponse.getTargetCurrencyCode().isBlank() ||
                exchangeRateDTOResponse.getRate() <= 0) {
            throw new Exception("не корректно заполнены поля");
        }
        try {
            exchangeRatesService.addExchangeRate(factoryService.convertExchangeDTOIntoExchange(exchangeRateDTOResponse));
            return new ResponseEntity<>(getLastElementIntoDB(), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new Exception("не удаётся подключиться к бд");
        }
    }

    @PatchMapping("/exchangeRate/{code}")
    public ResponseEntity<ExchangeRateDTORequest> updateExchangeRate(@PathVariable("code") String code, String rate) throws Exception {
        try {
            if (code.isBlank() || Double.parseDouble(rate) <= 0) {
                throw new Exception("некорректно введены данные");
            }
        } catch (NumberFormatException e) {
            throw new Exception("некорректно введены данные");
        }

        try {
            int baseId = factoryService.convertBaseId(code);
            int targetId = factoryService.convertTargetId(code);

            Currency baseCurrency = currenciesService.findById(baseId);
            Currency targetCurrency = currenciesService.findById(targetId);

            var exchangeRate = exchangeRatesService.findExchangeRate(baseCurrency, targetCurrency);

            exchangeRate.setRate(Double.parseDouble(rate));
            exchangeRatesService.addExchangeRate(exchangeRate);

            return new ResponseEntity<>(factoryService.converterExchangeRateIntoExchangeRateDTO(exchangeRate), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new Exception("не удается подключиться к бд");
        }
    }


    @GetMapping("/exchange")
    public ResponseEntity<ExchangeDTOAmount> getExchange(@RequestParam("from") String from,
                                                        @RequestParam("to") String to,
                                                         @RequestParam("amount") String amount) throws Exception {
        if (from == null || to == null || amount.isEmpty() || Double.parseDouble(amount) < 0) {
            throw new Exception("не корректно введены данные");
        }
        return new ResponseEntity<>(factoryService.getAmount(from, to, amount), HttpStatus.OK);
    }


    public ExchangeRateDTORequest getLastElementIntoDB() {
        int size = factoryService.getExchangeRateDTORequest(exchangeRatesService.findAll()).size();
        return factoryService.getExchangeRateDTORequest(exchangeRatesService.findAll()).get(size - 1);
    }
}
