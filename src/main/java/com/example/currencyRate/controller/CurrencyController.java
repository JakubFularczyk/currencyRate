package com.example.currencyRate.controller;

import com.example.currencyRate.dto.ExchangeRequest;
import com.example.currencyRate.service.CurrencyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exchange")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping
    public String convert(@RequestBody ExchangeRequest request) {
        return currencyService.convertToPlnFormatted(request.currency(), request.amount());
    }
}

