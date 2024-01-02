package org.example.domain;

// 메뉴 클래스는 이름, 설명 필드를 가지는 클래스로 만들어주세요.
public abstract class Menu {

    private String name;
    private String description;
    private int price;

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getPrice() {
        return this.price;
    }
}
