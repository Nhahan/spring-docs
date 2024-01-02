package org.example.domain;

// 주문 클래스도 만들어서 상품 객체를 담을 수 있도록 해주세요.
public class Order {

    private final Menu menu;
    private final int quantity;

    public Order(Menu menu, int quantity) {
        this.menu = menu;
        this.quantity = quantity;
    }

    public int calculatePrice() {
        return menu.getPrice() * quantity;
    }

    public String getName() {
        return menu.getName();
    }

    public String getDescription() {
        return menu.getDescription();
    }

    public int getPrice() {
        return menu.getPrice();
    }

    public int getQuantity() {
        return quantity;
    }
}
