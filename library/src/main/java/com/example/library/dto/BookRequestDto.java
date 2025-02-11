package com.example.library.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BookRequestDto {
    private String title;
    private List<Long> authorIds;
}
