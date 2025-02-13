package com.example.demo2.comment.dto;

import lombok.Getter;

@Getter
public class CommentSimpleResponseDto {

    private final Long id;
    private final String content;

    public CommentSimpleResponseDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
