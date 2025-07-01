package com.example.currencyRate.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class CurrencyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String currency;

    @NonNull
    @Column(nullable = false)
    private Double rate;

    @NonNull
    @Column(nullable = false)
    private LocalDate date;
}
