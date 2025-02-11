package com.example.post.dto;

import lombok.Getter;

@Getter
public class PostUpdateResponseDto {
    private final Long id;
    private final String title;

    public PostUpdateResponseDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
