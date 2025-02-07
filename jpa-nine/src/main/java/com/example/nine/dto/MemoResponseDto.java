package com.example.nine.dto;

import lombok.Getter;

@Getter
public class MemoResponseDto {

    private final Long id;
    private final String content;

    public MemoResponseDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
