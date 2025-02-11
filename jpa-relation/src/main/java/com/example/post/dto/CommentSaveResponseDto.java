package com.example.post.dto;

import lombok.Getter;

@Getter
public class CommentSaveResponseDto {
    private final Long id;
    private final String content;
    private final Long postId;

    public CommentSaveResponseDto(Long id, String content, Long postId) {
        this.id = id;
        this.content = content;
        this.postId = postId;
    }
}
