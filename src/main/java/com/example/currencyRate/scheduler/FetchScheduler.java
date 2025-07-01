package com.example.currencyRate.scheduler;

import com.example.currencyRate.service.CurrencyService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FetchScheduler {

    private final CurrencyService currencyService;

    public FetchScheduler(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Scheduled(cron = "0 0 2 * * * ")
    public void fetchDailyRates() {
        currencyService.fetchAndSaveRates();
    }
}
