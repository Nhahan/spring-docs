package com.example.demo.todo.dto;

import lombok.Getter;

@Getter
public class TodoUpdateResponseDto {

    private final Long id;
    private final String content;

    public TodoUpdateResponseDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
