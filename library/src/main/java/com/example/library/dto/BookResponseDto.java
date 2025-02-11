package com.example.library.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BookResponseDto {
    private Long id;
    private String title;
    private List<AuthorResponseDto> authors;

    public BookResponseDto(Long id, String title, List<AuthorResponseDto> authors) {
        this.id = id;
        this.title = title;
        this.authors = authors;
    }
}
