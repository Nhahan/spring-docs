package com.example.post.dto;

import lombok.Getter;

@Getter
public class PostSaveRequestDto {
    private final String title;

    public PostSaveRequestDto(String title) {
        this.title = title;
    }
}
