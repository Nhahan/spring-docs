package com.example.demo2.post.dto;

import lombok.Getter;

@Getter
public class PostSimpleResponseDto {

    private final Long id;
    private final String title;
    private final String content;

    public PostSimpleResponseDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
