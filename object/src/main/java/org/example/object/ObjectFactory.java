package org.example.object;

public class ObjectFactory {
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider());
    }

    public IExRateProvider exRateProvider() {
        return new SimpleExRateProvider();
    }
}
