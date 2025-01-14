package com.example.objectdemo;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class ObjectDemoApplication {

    public static void main(String[] args) {
        PaymentService paymentService = new PaymentService(new ExRateProvider());
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(55.5));
        System.out.println(payment);
    }

}
