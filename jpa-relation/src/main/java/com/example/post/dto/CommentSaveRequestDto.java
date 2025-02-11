package com.example.post.dto;

import lombok.Getter;

@Getter
public class CommentSaveRequestDto {
    private final String content;
    private final Long postId;

    public CommentSaveRequestDto(String content, Long postId) {
        this.content = content;
        this.postId = postId;
    }
}
