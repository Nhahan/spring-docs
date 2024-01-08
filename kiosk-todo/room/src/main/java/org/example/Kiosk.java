package org.example;

import java.util.Scanner;

public class Kiosk {

    public Kiosk() {
    }

    public void run() {
        System.out.println("우리 가게에 오신 것을 환영합니다.");
        System.out.println("키오스크로 주문 부탁드리겠습니다.");

        Cart cart = new Cart();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Burger\n2. Drink\n3. 주문하기\n4. 종료");
            int i = sc.nextInt();

            if (i == 1) { // Burger 선택
                System.out.println("1. CheeseBurger / 2. BeefBurger / 3. ChickenBurger");
                int select = sc.nextInt();

                if (select == 1) {
                    Burger cheeseBurger = new Burger("CheeseBurger", 3000);
                    cart.addMenu(cheeseBurger);
                } else if (select == 2) {
                    Burger beefBurger = new Burger("BeefBurger", 3500);
                    cart.addMenu(beefBurger);
                } else if (select == 3) {
                    Burger chickenBurger = new Burger("ChickenBurger", 3200);
                    cart.addMenu(chickenBurger);
                } else {
                    System.out.println("잘못된 입력입니다.");
                }
            } else if (i == 2) { // Drink 선택
                System.out.println("1. Water / 2. Coke");

                int select = sc.nextInt();

                if (select == 1) {
                    Drink water = new Drink("Water", 1000);
                    cart.addMenu(water);
                } else if (select == 2) {
                    Drink coke = new Drink("Coke", 2000);
                    cart.addMenu(coke);
                } else {
                    System.out.println("잘못된 입력입니다.");
                }
            } else if (i == 3) { // 주문하기 선택
                if (cart.isEmpty()) {
                    System.out.println("주문을 먼저 해주세요.");
                } else {
                    cart.pay();
                    break;
                }
            } else {
                break;
            }
        }
    }
}
