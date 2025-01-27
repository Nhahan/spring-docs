package com.example.objectdemo;

import java.math.BigDecimal;

public class ObjectDemoApplication {
    public static void main(String[] args) {
        ObjectFactory objectFactory = new ObjectFactory();
        PaymentService paymentService = objectFactory.paymentService();
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(55.5));
        System.out.println(payment);
    }
}
