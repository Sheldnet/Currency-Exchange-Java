package com.example.currencyexchangejava.Controllers;

import com.example.currencyexchangejava.Entities.Currency;
import com.example.currencyexchangejava.Entities.Test;
import com.example.currencyexchangejava.Repositories.CurrencyRepository;
import com.example.currencyexchangejava.Repositories.TestRepository;
import com.example.currencyexchangejava.Service.CurrenciesService;
import com.example.currencyexchangejava.Service.FactoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestRepository testRepository;

    @GetMapping("/test")
    public List<Test> getCurrencies() {
        System.err.println(testRepository.findAll());
           return testRepository.findAll();
    }
}
