package com.example.currencyRate.scheduler;

import com.example.currencyRate.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FetchScheduler {

    private final CurrencyService currencyService;

    @Scheduled(cron = "0 0 2 * * * ")
    public void fetchDailyRates() {
        currencyService.fetchAndSaveRates();
    }
}
