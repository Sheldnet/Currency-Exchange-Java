package com.example.currencyexchangejava.Service;

import com.example.currencyexchangejava.DTO.CurrencyDTO;
import com.example.currencyexchangejava.DTO.ExchangeDTOAmount;
import com.example.currencyexchangejava.DTO.ExchangeRateDTOResponse;
import com.example.currencyexchangejava.DTO.ExchangeRateDTORequest;
import com.example.currencyexchangejava.Entities.Currency;
import com.example.currencyexchangejava.Entities.ExchangeRate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public interface FactoryService {

    public Currency convertCurrencyDTOToCurrency(CurrencyDTO currencyDTO);

    public List<ExchangeRateDTORequest> getExchangeRateDTORequest(List<ExchangeRate> exchangeRateList);

    public ExchangeRateDTORequest converterExchangeRateIntoExchangeRateDTO(ExchangeRate exchangeRate);

    public int convertBaseId(String code) throws Exception;

    public String getConvertStringBaseCode(String line) throws Exception;

    public int convertTargetId(String code) throws Exception;

    public String getConvertStringTargetCode(String line) throws Exception;

    public ExchangeRate convertExchangeDTOIntoExchange(ExchangeRateDTOResponse exchangeRateDTOResponse);

    public ExchangeDTOAmount getAmount(String from, String to, String amount) throws Exception;

    private static double roundDoubles(double number) {
        BigDecimal bd = new BigDecimal(Double.toString(number));
        bd = bd.setScale(2, RoundingMode.DOWN);
        return bd.doubleValue();
    }


}
