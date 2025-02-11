package com.example.post.dto;

import lombok.Getter;

@Getter
public class PostSaveResponseDto {
    private final Long id;
    private final String title;

    public PostSaveResponseDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
