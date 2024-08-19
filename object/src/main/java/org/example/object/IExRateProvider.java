package org.example.object;

import java.math.BigDecimal;

public interface IExRateProvider {
    public BigDecimal getExRate(String currency);
}
