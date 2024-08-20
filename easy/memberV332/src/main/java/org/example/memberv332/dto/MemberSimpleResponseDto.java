package org.example.memberv332.dto;

import lombok.Getter;

@Getter
public class MemberSimpleResponseDto {
    private final Long id;
    private final String name;

    public MemberSimpleResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
