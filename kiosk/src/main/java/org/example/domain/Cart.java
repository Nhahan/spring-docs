package org.example.domain;

import java.util.List;

// 주문 클래스도 만들어서 상품 객체를 담을 수 있도록 해주세요.
public class Cart {

    private final List<Order> orderList;

    public Cart(List<Order> orderList) {
        this.orderList = orderList;
    }

    public int calculateTotalPrice() {
        int totalPrice = 0;
        for (Order order : orderList) {
            totalPrice += order.calculatePrice();
        }
        return totalPrice;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void addOrder(Order order) {
        orderList.add(order);
    }

    public void removeOrder(Order order) {
        orderList.remove(order);
    }

    public void clear() {
        orderList.clear();
    }
}
