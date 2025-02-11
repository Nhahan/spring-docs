package com.example.library.repository;

import com.example.library.entity.Book;
import com.example.library.entity.BookAuthor;
import com.example.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {
    List<BookAuthor> findByBook(Book book);
    List<BookAuthor> findByAuthor(Author author);
    void deleteByBook(Book book);
}
