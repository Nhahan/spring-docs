package com.example.member.dto;

import lombok.Getter;

@Getter
public class MemberRequestDto {

    private final String email;
    private final String password;

    public MemberRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
