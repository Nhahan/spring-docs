package com.example.objectdemo;

import java.math.BigDecimal;

public interface IExRateProvider {
    BigDecimal getExRate(String currency);
}
