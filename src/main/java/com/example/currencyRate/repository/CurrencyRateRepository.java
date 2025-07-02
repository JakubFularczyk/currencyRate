package com.example.currencyRate.repository;

import com.example.currencyRate.model.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    Optional<CurrencyRate> findTopByCurrencyOrderByDateDesc(String currency);
    void deleteByDateBefore(java.time.LocalDate date);

}
