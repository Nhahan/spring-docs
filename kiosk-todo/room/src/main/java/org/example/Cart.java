package org.example;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<Menu> menuList = new ArrayList<>();

    public Cart() {
    }

    public void addMenu(Menu menu) {
        menuList.add(menu);
    }

    public boolean isEmpty() {
        if (menuList.isEmpty()) {
            return true;
        }
        return false;
    }

    public void pay() {
        int totalPrice = 0;
        for (Menu menu : menuList) {
            totalPrice += menu.getPrice();
        }
        System.out.println("총 결제 금액은 " + totalPrice + "원 입니다.");
    }
}
