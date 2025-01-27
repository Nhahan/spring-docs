package com.example.objectdemo;

public class ObjectFactory {
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider());
    }

    public IExRateProvider exRateProvider() {
        return new SimpleExRateProvider();
    }
}
