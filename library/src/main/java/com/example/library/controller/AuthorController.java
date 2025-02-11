package com.example.library.controller;

import com.example.library.dto.AuthorRequestDto;
import com.example.library.dto.AuthorResponseDto;
import com.example.library.dto.BookResponseDto;
import com.example.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/authors")
    public ResponseEntity<AuthorResponseDto> createAuthor(@RequestBody AuthorRequestDto requestDto) {
        AuthorResponseDto result = authorService.createAuthor(requestDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorResponseDto> getAuthor(@PathVariable Long id) {
        AuthorResponseDto result = authorService.getAuthor(id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/authors/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }

    @GetMapping("/authors/{id}/books")
    public ResponseEntity<List<BookResponseDto>> getBooksByAuthor(@PathVariable Long id) {
        List<BookResponseDto> result = authorService.getBooksByAuthor(id);
        return ResponseEntity.ok(result);
    }
}
