package com.example.post.dto;

import lombok.Getter;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final String content;
    private final Long postId;

    public CommentResponseDto(Long id, String content, Long postId) {
        this.id = id;
        this.content = content;
        this.postId = postId;
    }
}
