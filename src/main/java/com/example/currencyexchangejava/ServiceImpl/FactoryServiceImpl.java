package com.example.currencyexchangejava.ServiceImpl;

import com.example.currencyexchangejava.DTO.CurrencyDTO;
import com.example.currencyexchangejava.DTO.ExchangeDTOAmount;
import com.example.currencyexchangejava.DTO.ExchangeRateDTOResponse;
import com.example.currencyexchangejava.DTO.ExchangeRateDTORequest;
import com.example.currencyexchangejava.Entities.Currency;
import com.example.currencyexchangejava.Entities.ExchangeRate;
import com.example.currencyexchangejava.Service.CurrenciesService;
import com.example.currencyexchangejava.Service.ExchangeRatesService;
import com.example.currencyexchangejava.Service.FactoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class FactoryServiceImpl implements FactoryService {
    private final CurrenciesService currenciesService;
    private final ExchangeRatesService exchangeRatesService;
    private final ModelMapper modelMapper;

    @Autowired
    public FactoryServiceImpl(CurrenciesService currenciesService, ExchangeRatesService exchangeRatesService, ModelMapper modelMapper) {
        this.currenciesService = currenciesService;
        this.exchangeRatesService = exchangeRatesService;
        this.modelMapper = modelMapper;
    }


    @Override
    public Currency convertCurrencyDTOToCurrency(CurrencyDTO currencyDTO) {
        return modelMapper.map(currencyDTO, Currency.class);
    }

    @Override
    public List<ExchangeRateDTORequest> getExchangeRateDTORequest(List<ExchangeRate> exchangeRateList) {
        List<ExchangeRateDTORequest> exchangeRateDTORequests = new ArrayList<>();

        for (ExchangeRate exchangeRate : exchangeRateList) {
            exchangeRateDTORequests.add(converterExchangeRateIntoExchangeRateDTO(exchangeRate));
        }
        return exchangeRateDTORequests;
    }

    @Override
    public ExchangeRateDTORequest converterExchangeRateIntoExchangeRateDTO(ExchangeRate exchangeRate) {
        Currency baseCurrency = exchangeRate.getBaseCurrencyId();
        Currency targetCurrency = exchangeRate.getTargetCurrencyId();

        return new ExchangeRateDTORequest(
                exchangeRate.getId(),
                baseCurrency,
                targetCurrency,
                exchangeRate.getRate()
        );
    }

    @Override
    public int convertBaseId(String code) throws Exception {
        String baseCurrencyCode = getConvertStringBaseCode(code);
        Currency baseCurrency = currenciesService.findByCode(baseCurrencyCode);
        if (baseCurrency == null) {
            throw new Exception("Не найден обменный курс для валюты " + baseCurrencyCode);
        }
        return baseCurrency.getId();
    }

    @Override
    public int convertTargetId(String code) throws Exception {
        String targetCurrencyCode = getConvertStringTargetCode(code);
        Currency targetCurrency = currenciesService.findByCode(targetCurrencyCode);
        if (targetCurrency == null) {
            throw new Exception("Не найден обменный курс для валюты " + targetCurrencyCode);
        }
        return targetCurrency.getId();
    }


    @Override
    public String getConvertStringBaseCode(String line) throws Exception {
        if (line.length() != 6 && line.length() != 3) {
            throw new Exception("Некорректно указаны валюты");
        }
        return (line.length() == 3) ? line : line.substring(0, 3);
    }

    @Override
    public String getConvertStringTargetCode(String line) throws Exception {
        if (line.length() != 6 && line.length() != 3) {
            throw new Exception("Некорректно указаны валюты");
        }
        return (line.length() == 3) ? line : line.substring(3, 6);
    }


    @Override
    public ExchangeRate convertExchangeDTOIntoExchange(ExchangeRateDTOResponse exchangeRateDTOResponse) {
        Currency base = currenciesService.findByCode(exchangeRateDTOResponse.getBaseCurrencyCode());
        Currency target = currenciesService.findByCode(exchangeRateDTOResponse.getTargetCurrencyCode());
       return new ExchangeRate(base, target, exchangeRateDTOResponse.getRate());
    }

    @Override
    public ExchangeDTOAmount getAmount(String from, String to, String amount) throws Exception {
        String base = "USD";
        double amountDouble = Double.parseDouble(amount);
        double rate = 0;
        double answer = 0.0;

        Currency baseCurrency = currenciesService.findByCode(from);
        Currency targetCurrency = currenciesService.findByCode(to);

        ExchangeRate exchangeRate = exchangeRatesService.findExchangeRate(baseCurrency, targetCurrency);
        if (exchangeRate != null) {
            rate = exchangeRate.getRate();
            answer = roundDoubles(amountDouble * rate);
        } else {
            Currency inverseBaseCurrency = currenciesService.findByCode(to);
            Currency inverseTargetCurrency = currenciesService.findByCode(from);
            ExchangeRate inverseExchangeRate = exchangeRatesService.findExchangeRate(inverseBaseCurrency, inverseTargetCurrency);

            if (inverseExchangeRate != null) {
                rate = 1 / inverseExchangeRate.getRate();
                answer = roundDoubles(rate * amountDouble);
            } else {
                try {
                    double usdFromRate = exchangeRatesService.findExchangeRate(currenciesService.findByCode(base + from), baseCurrency).getRate();
                    double usdToRate = exchangeRatesService.findExchangeRate(currenciesService.findByCode(base + to), baseCurrency).getRate();
                    rate = 1 / (usdFromRate / usdToRate);
                    answer = roundDoubles(rate * amountDouble);
                } catch (RuntimeException e) {
                    throw new Exception("Курса с такими валютами нет");
                }
            }
        }

        return new ExchangeDTOAmount(
                baseCurrency,
                targetCurrency,
                new BigDecimal(Double.toString(rate)).setScale(2, RoundingMode.HALF_DOWN),
                amount,
                answer);
    }

    private static double roundDoubles(double number) {
        BigDecimal bd = new BigDecimal(Double.toString(number));
        bd = bd.setScale(2, RoundingMode.DOWN);
        return bd.doubleValue();
    }
}
