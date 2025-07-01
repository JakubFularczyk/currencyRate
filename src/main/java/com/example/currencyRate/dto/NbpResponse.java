package com.example.currencyRate.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class NbpResponse {
    private String table;
    private String currency;
    private String code;
    private List<Rate> rates;

    @Data
    public static class Rate {
        private String no;
        private LocalDate effectiveDate;
        private Double mid;
    }
}

