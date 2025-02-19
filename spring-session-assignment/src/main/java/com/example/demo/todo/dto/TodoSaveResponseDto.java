package com.example.demo.todo.dto;

import lombok.Getter;

@Getter
public class TodoSaveResponseDto {

    private final Long id;
    private final String content;
    private final Long memberId;
    private final String memberEmail;

    public TodoSaveResponseDto(Long id, String content, Long memberId, String memberEmail) {
        this.id = id;
        this.content = content;
        this.memberId = memberId;
        this.memberEmail = memberEmail;
    }
}
