package com.example.library.service;

import com.example.library.dto.AuthorRequestDto;
import com.example.library.dto.AuthorResponseDto;
import com.example.library.dto.BookResponseDto;
import com.example.library.entity.Author;
import com.example.library.entity.BookAuthor;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookAuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookAuthorRepository bookAuthorRepository;

    @Transactional
    public AuthorResponseDto createAuthor(AuthorRequestDto requestDto) {
        if (requestDto.getName() == null) {
            throw new IllegalArgumentException("Name is required.");
        }
        Author author = new Author(requestDto.getName());
        Author saved = authorRepository.save(author);
        return new AuthorResponseDto(saved.getId(), saved.getName());
    }

    @Transactional(readOnly = true)
    public AuthorResponseDto getAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Author not found with id " + id));
        return new AuthorResponseDto(author.getId(), author.getName());
    }

    @Transactional
    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Author not found with id " + id));
        authorRepository.delete(author);
    }

    @Transactional(readOnly = true)
    public List<BookResponseDto> getBooksByAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalStateException("Author not found with id " + authorId));
        List<BookAuthor> mappings = bookAuthorRepository.findByAuthor(author);
        return mappings.stream()
                .map(ba -> new BookResponseDto(ba.getBook().getId(), ba.getBook().getTitle(), null))
                .collect(Collectors.toList());
    }
}
