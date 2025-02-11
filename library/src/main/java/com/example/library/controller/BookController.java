package com.example.library.controller;

import com.example.library.dto.BookRequestDto;
import com.example.library.dto.BookResponseDto;
import com.example.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/books")
    public ResponseEntity<BookResponseDto> createBook(@RequestBody BookRequestDto requestDto) {
        BookResponseDto result = bookService.createBook(requestDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookResponseDto> getBook(@PathVariable Long id) {
        BookResponseDto result = bookService.getBook(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable Long id, @RequestBody BookRequestDto requestDto) {
        BookResponseDto result = bookService.updateBook(id, requestDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

}
