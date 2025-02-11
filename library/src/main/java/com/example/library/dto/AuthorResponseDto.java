package com.example.library.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthorResponseDto {
    private Long id;
    private String name;

    public AuthorResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
