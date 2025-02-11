package com.example.library.service;

import com.example.library.dto.AuthorResponseDto;
import com.example.library.dto.BookRequestDto;
import com.example.library.dto.BookResponseDto;
import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.entity.BookAuthor;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookAuthorRepository;
import com.example.library.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookAuthorRepository bookAuthorRepository;

    @Transactional
    public BookResponseDto createBook(BookRequestDto requestDto) {
        if (requestDto.getTitle() == null) {
            throw new IllegalArgumentException("Title is required.");
        }

        Book book = new Book(requestDto.getTitle());
        Book savedBook = bookRepository.save(book);

        // 각 author id에 대해 bookAuthor 중간테이블 저장
        if (requestDto.getAuthorIds() != null) {
            for (Long authorId : requestDto.getAuthorIds()) {
                Author author = authorRepository.findById(authorId)
                        .orElseThrow(() -> new IllegalStateException("Author not found with id " + authorId));
                BookAuthor bookAuthor = new BookAuthor(savedBook, author);
                bookAuthorRepository.save(bookAuthor);
            }
        }
        return convertToResponseDto(savedBook);
    }

    @Transactional(readOnly = true)
    public BookResponseDto getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Book not found with id " + id));
        return convertToResponseDto(book);
    }

    @Transactional
    public BookResponseDto updateBook(Long id, BookRequestDto requestDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Book not found with id " + id));

        // 요청에 title이 포함되어 있다면 업데이트
        if (requestDto.getTitle() != null) {
            book.updateTitle(requestDto.getTitle());
        }

        // 요청에 authorIds가 포함되어 있다면 기존 매핑을 삭제 후 재설정
        if (requestDto.getAuthorIds() != null) {
            // 기존 BookAuthor 매핑 삭제
            bookAuthorRepository.deleteByBook(book);

            // 요청에 포함된 각 authorId에 대해 새로운 매핑 생성
            for (Long authorId : requestDto.getAuthorIds()) {
                Author author = authorRepository.findById(authorId)
                        .orElseThrow(() -> new EntityNotFoundException("Author not found with id " + authorId));
                BookAuthor bookAuthor = new BookAuthor(book, author);
                bookAuthorRepository.save(bookAuthor);
            }
        }
        Book updated = bookRepository.save(book);
        return convertToResponseDto(updated);
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Book not found with id " + id));
        bookAuthorRepository.deleteByBook(book);
        bookRepository.delete(book);
    }

    private BookResponseDto convertToResponseDto(Book book) {
        List<AuthorResponseDto> authors = bookAuthorRepository.findByBook(book).stream()
                .map(ba -> new AuthorResponseDto(ba.getAuthor().getId(), ba.getAuthor().getName()))
                .collect(Collectors.toList());
        return new BookResponseDto(book.getId(), book.getTitle(), authors);
    }
}
