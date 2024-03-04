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
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
            String baseCurrencyCode = code.substring(0, 3); // Получаем код базовой валюты
            String targetCurrencyCode = code.substring(3); // Получаем код целевой валюты

            Currency baseCurrency = currenciesService.findByCode(baseCurrencyCode);
            Currency targetCurrency = currenciesService.findByCode(targetCurrencyCode);

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

/*    @GetMapping("/exchangeRate/{exchangeRate}")
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
    }*/

    //TODO: Разобраться с патчем
    @PostMapping("/exchangeRates")
    public ResponseEntity<ExchangeRateDTORequest> addExchangeRate(@RequestBody ExchangeRateDTOResponse exchangeRateDTOResponse) throws Exception {
        if (exchangeRateDTOResponse.getBaseCurrencyCode() == null ||
                exchangeRateDTOResponse.getBaseCurrencyCode().trim().isEmpty() ||
                exchangeRateDTOResponse.getTargetCurrencyCode() == null ||
                exchangeRateDTOResponse.getTargetCurrencyCode().trim().isEmpty() ||
                exchangeRateDTOResponse.getRate() <= 0) {
            throw new Exception("Некорректно заполнены поля");
        }
        try {
            exchangeRatesService.addExchangeRate(factoryService.convertExchangeDTOIntoExchange(exchangeRateDTOResponse));
            return new ResponseEntity<>(getLastElementIntoDB(), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new Exception("Не удается подключиться к БД");
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


//    @GetMapping("/exchange/{from}/{to}")
//    public ResponseEntity<ExchangeDTOAmount> getExchange(@RequestParam("from") String from,
//                                                        @RequestParam("to") String to,
//                                                         @RequestParam("amount") String amount) throws Exception {
//        if (from == null || to == null || amount.isEmpty() || Double.parseDouble(amount) < 0) {
//            throw new Exception("не корректно введены данные");
//        }
//        return new ResponseEntity<>(factoryService.getAmount(from, to, amount), HttpStatus.OK);
//    }


    public ExchangeRateDTORequest getLastElementIntoDB() {
        int size = factoryService.getExchangeRateDTORequest(exchangeRatesService.findAll()).size();
        return factoryService.getExchangeRateDTORequest(exchangeRatesService.findAll()).get(size - 1);
    }
    @GetMapping("/exchange")
    public ResponseEntity<?> getExchange(@RequestParam("from") String from,
                                         @RequestParam("to") String to,
                                         @RequestParam("amount") double amount) throws Exception {
        try {
            // Получаем обменный курс для валюты from в валюту to
            int baseId = factoryService.convertBaseId(from);
            int targetId = factoryService.convertTargetId(to);

            Currency baseCurrency = currenciesService.findById(baseId);
            Currency targetCurrency = currenciesService.findById(targetId);
            ExchangeRate exchangeRate = exchangeRatesService.findExchangeRate(baseCurrency, targetCurrency);

            if (exchangeRate != null) {
                double rate = exchangeRate.getRate();
                double convertedAmount = amount * rate;

                // Формируем ответ
                ExchangeDTOAmount exchangeDTOAmount = new ExchangeDTOAmount(
                        baseCurrency,
                        targetCurrency,
                        BigDecimal.valueOf(rate),
                        Double.toString(amount),
                        convertedAmount
                );

                return ResponseEntity.ok(exchangeDTOAmount);
            } else {
                // Обработка случая, когда обменный курс не найден
                return ResponseEntity.badRequest().body(new Exception("курс не найден"));
            }
        } catch (Exception e) {
            // Обработка других возможных ошибок
            throw new Exception("Ошибка при конвертации валют: " + e.getMessage());
        }
    }


}
