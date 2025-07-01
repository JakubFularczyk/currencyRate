package com.example.currencyRate.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class CurrencyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private Double rate;

    @Column(nullable = false)
    private LocalDate date;

    public CurrencyRate(String currency, Double rate, LocalDate date) {
        this.currency = currency;
        this.rate = rate;
        this.date = date;
    }


}
