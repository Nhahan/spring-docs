package com.example.objectdemo;

import java.math.BigDecimal;

public class SimpleExRateProvider implements IExRateProvider {
    public BigDecimal getExRate(String currency) {
        if (currency.equals("USD")) {
            return new BigDecimal("1000");
        }

        throw new IllegalArgumentException("Unsupported currency: " + currency);
    }
}
