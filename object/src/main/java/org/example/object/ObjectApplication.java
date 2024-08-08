package org.example.object;

import java.math.BigDecimal;

public class ObjectApplication {

    public static void main(String[] args) {
        PaymentService paymentService = new PaymentService(new SimpleExRateProvider());
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(55.5));
        System.out.println(payment);
    }
}
