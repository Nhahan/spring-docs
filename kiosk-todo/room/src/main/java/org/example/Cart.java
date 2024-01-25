package org.example;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<Menu> list = new ArrayList<>();

    public void add(Menu menu) {
        list.add(menu);
    }

}
