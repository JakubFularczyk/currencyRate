package com.example.currencyRate.service;

import com.example.currencyRate.dto.NbpResponse;
import com.example.currencyRate.model.CurrencyCode;
import com.example.currencyRate.model.CurrencyRate;
import com.example.currencyRate.repository.CurrencyRateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
@Transactional
public class CurrencyService {
    private final CurrencyRateRepository repository;
    private final WebClient webClient;
    @Value("${NBP_URL}")
    private String nbpUrl;

    public void fetchAndSaveRates() {

        repository.deleteByDateBefore(LocalDate.now());
        for (CurrencyCode currency : CurrencyCode.values()) {
            String code = currency.name();
            String url = nbpUrl + currency + "/?format=json";

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
