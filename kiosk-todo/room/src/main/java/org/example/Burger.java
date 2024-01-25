package org.example;

public class Burger extends Menu {

    private String name;

    public Burger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// 1. 명시적이지 않아도 누구나 다 알 수 있어야함 (반드시 구현하지 않아도 됨)
// 2. 추상화 클래스 자체만으로 객체를 생성할 일이 없어야함 (즉, 반드시 상속용으로만 쓰여야함)