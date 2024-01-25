package org.example;

import java.util.Scanner;

public class Kiosk {
    public void run() {
        Cart cart = new Cart();

        System.out.println("우리 가게에 오신 것을 환영합니다.");
        System.out.println("원하시는 메뉴를 선택해주세요.");

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Burger\n2. Drink\n3. 주문하기\n4. 나가기");
            int i = sc.nextInt();

            if (i == 1) {
                System.out.println("원하시는 Burger를 선택해주세요.");
                System.out.println("1. Cheese Burger\n2. Beef Burger\n3. Chicken Burger");
                int j = sc.nextInt();

                if (j == 1) {
                    System.out.println("Cheese Burger를 선택하셨습니다.");
                    Burger cheeseBurger = new Burger("Cheese Burger");
                    cart.add(cheeseBurger);
                } else if (j == 2) {
                    System.out.println("Beef Burger를 선택하셨습니다.");
                    Burger beefBurger = new Burger("Beef Burger");
                    cart.add(beefBurger);
                } else if (j == 3) {
                    System.out.println("Chicken Burger를 선택하셨습니다.");
                    Burger chickenBurger = new Burger("Chicken Burger");
                    cart.add(chickenBurger);
                } else {
                    System.out.println("잘못된 입력입니다.");
                }
            } else if (i == 2) {
                System.out.println("원하시는 Drink를 선택해주세요.");
                System.out.println("1. Coke\n2. Juice");
                int j = sc.nextInt();

                if (j == 1) {
                    System.out.println("Coke를 선택하셨습니다.");
                    Drink coke = new Drink("Coke");
                    cart.add(coke);
                } else if (j == 2) {
                    System.out.println("Juice를 선택하셨습니다.");
                    Drink juice = new Drink("Juice");
                    cart.add(juice);
                } else {
                    System.out.println("잘못된 입력입니다.");
                }
            } else if (i == 3) {
                System.out.println("주문이 완료되었습니다.");
                break;
            } else if (i == 4) {
                System.out.println("이용해주셔서 감사합니다.");
                break;
            } else {
                System.out.println("잘못된 입력입니다. 다시 입력해주세요");
            }
        }
    }
}
