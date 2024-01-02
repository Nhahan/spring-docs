package org.example.service;

import org.example.data.ScreenData;
import org.example.data.ScreenStage;

public class ScreenService {

    private final ScreenData screenData = new ScreenData();

    public ScreenService() { defaultBehavior(); }

    public void on() {
        print(screenData.getScreenData(ScreenStage.MAIN));
    }

    public void print(String screenData) {
        System.out.println(screenData);
    }

    public void defaultBehavior() {
        print("\n\n==================================\n\n");
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
                print("\n\n==================================\n\n")
        ));
    }
}
