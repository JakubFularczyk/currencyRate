package com.example.currencyRate.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ExchangeRequest(

        @NotBlank(message = "Currency code must not be blank")
        @Pattern(regexp = "^[A-Za-z]{3}$", message = "Currency code must be a 3-letter code")
        String currency,

        @Min(value = 0, message = "Amount must be non-negative")
        double amount

) {}
