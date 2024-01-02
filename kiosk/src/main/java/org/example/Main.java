package org.example;

import org.example.service.ScreenService;

public class Main {
    public static void main(String[] args) {
        ScreenService kioskService = new ScreenService();

        kioskService.on();
    }
}