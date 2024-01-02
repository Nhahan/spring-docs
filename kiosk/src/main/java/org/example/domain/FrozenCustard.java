package org.example.domain;

// 상품 클래스는 이름, 가격, 설명 필드를 가지는 클래스로 만들어주세요.
// 상품 클래스의 이름, 설명 필드는 메뉴 클래스를 상속받아 사용하는 구조로 개발해주세요.
public class FrozenCustard extends Menu {

    private final String name;
    private final String description;
    private final int price;

    public FrozenCustard(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}
