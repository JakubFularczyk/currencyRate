package com.example.currencyRate.service;

import com.example.currencyRate.dto.NbpResponse;
import com.example.currencyRate.model.CurrencyCode;
import com.example.currencyRate.model.CurrencyRate;
import com.example.currencyRate.repository.CurrencyRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class CurrencyService {
    private final CurrencyRateRepository repository;
    private final WebClient webClient;

    public void fetchAndSaveRates() {

        for (CurrencyCode currency : CurrencyCode.values()) {
            String code = currency.name();
            String url = "https://api.nbp.pl/api/exchangerates/rates/a/" + currency + "/?format=json";

            NbpResponse response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(NbpResponse.class)
                    .block();

            if (response != null && response.getRates() != null && !response.getRates().isEmpty()) {
                NbpResponse.Rate rate = response.getRates().getFirst();
                CurrencyRate currencyRate = new CurrencyRate(code, rate.getMid(), rate.getEffectiveDate());
                repository.save(currencyRate);
            }
        }
    }

    public String convertToPlnFormatted(String currency, double amount) {
        CurrencyRate rate = repository.findTopByCurrencyOrderByDateDesc(currency.toUpperCase())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Currency not found in DB: " + currency));

        double result = amount * rate.getRate();
        return String.format("%.2f %s = %.2f PLN", amount, currency.toUpperCase(), result);
    }

}
