package com.example.demo2.comment.dto;

import lombok.Getter;

@Getter
public class CommentResponseDto {

    private final Long id;
    private final Long postId;
    private final String content;

    public CommentResponseDto(Long id, Long postId, String content) {
        this.id = id;
        this.postId = postId;
        this.content = content;
    }
}
