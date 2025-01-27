package com.example.jdbc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Memo {

    @Setter
    private Long id;
    private String content;

    public Memo(String content) {
        this.content = content;
    }
}
