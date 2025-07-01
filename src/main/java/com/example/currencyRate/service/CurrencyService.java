package com.example.currencyRate.service;

import com.example.currencyRate.model.CurrencyRate;
import com.example.currencyRate.repository.CurrencyRateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyService {

    private final CurrencyRateRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();

    public CurrencyService(CurrencyRateRepository repository) {
        this.repository = repository;
    }

    public void fetchAndSaveRates() {
        List<String> currencies = List.of("eur", "usd", "chf", "gbp");

        for (String currency : currencies) {
            String url = "https://api.nbp.pl/api/exchangerates/rates/a/" + currency + "/?format=json";
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && response.containsKey("rates")) {
                List<Map<String, Object>> rates = (List<Map<String, Object>>) response.get("rates");
                if (!rates.isEmpty()) {
                    Double rate = ((Number) rates.get(0).get("mid")).doubleValue();
                    String dateStr = (String) rates.get(0).get("effectiveDate");

                    CurrencyRate currencyRate = new CurrencyRate(currency, rate, LocalDate.parse(dateStr));
                    repository.save(currencyRate);
                }
            }
        }
    }

    public double convertToPln(String currency, double amount) {
        CurrencyRate rate = repository.findTopByCurrencyOrderByDateDesc(currency.toUpperCase())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Currency not found in DB: " + currency));
        return amount * rate.getRate();
    }
}
