package com.example.library.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;

    public UserResponseDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
