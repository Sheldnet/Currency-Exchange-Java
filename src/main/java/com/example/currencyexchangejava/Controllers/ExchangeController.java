package com.example.currencyexchangejava.Controllers;

import com.example.currencyexchangejava.DTO.ExchangeDTOAmount;
import com.example.currencyexchangejava.DTO.ExchangeRateDTO;
import com.example.currencyexchangejava.DTO.ExchangeRateDTORequest;
import com.example.currencyexchangejava.Entities.ExchangeRate;
import com.example.currencyexchangejava.Service.ExchangeRatesService;
import com.example.currencyexchangejava.Service.FactoryService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExchangeController {

    private final ExchangeRatesService exchangeRatesService;
    private final FactoryService factoryService;

    public ExchangeController(ExchangeRatesService exchangeRatesService, FactoryService factoryService) {
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
            throw new Exception("не введены данные");
        }
        try {
            return new ResponseEntity<>(factoryService.converterExchangeRateIntoExchangeRateDTO(
                    exchangeRatesService.findExchangeRate(factoryService.convertBaseId(code),
                            factoryService.convertTargetId(code))), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new Exception("не удаётся подключиться к бд");
        }
    }

    @PostMapping("/exchangeRates")
    public ResponseEntity<ExchangeRateDTORequest> addExchangeRate(ExchangeRateDTO exchangeRateDTO) throws Exception {
        if (exchangeRateDTO.getBaseCurrencyCode().isBlank() ||
                exchangeRateDTO.getTargetCurrencyCode().isBlank() ||
                exchangeRateDTO.getRate() <= 0) {
            throw new Exception("не корректно заполнены поля");
        }
        try {
            exchangeRatesService.addExchangeRate(factoryService.convertExchangeDTOIntoExchange(exchangeRateDTO));
            return new ResponseEntity<>(getLastElementIntoDB(), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new Exception("не удаётся подключиться к бд");
        }
    }

    @PatchMapping("/exchangeRate/{code}")
    public ResponseEntity<ExchangeRateDTORequest> updateExchangeRate(@PathVariable("code") String code, String rate) throws Exception {
        try {
            if (code.isBlank() || Double.parseDouble(rate) <= 0) {
                throw new Exception("не корректно введены данные");
            }
        } catch (NumberFormatException e) {
            throw new Exception("не корректно введены данные");
        }

        try {
            var exchangeRate = exchangeRatesService.findExchangeRate(
                    factoryService.convertBaseId(code), factoryService.convertTargetId(code));

            exchangeRate.setRate(Double.parseDouble(rate));
            exchangeRatesService.addExchangeRate(exchangeRate);
            return new ResponseEntity<>(factoryService.converterExchangeRateIntoExchangeRateDTO(
                    exchangeRatesService.findExchangeRate(factoryService.convertBaseId(code),
                            factoryService.convertTargetId(code))), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new Exception("не удаётся подключиться к бд");
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
