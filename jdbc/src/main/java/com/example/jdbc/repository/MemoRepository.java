package com.example.jdbc.repository;

import com.example.jdbc.entity.Memo;

import java.util.List;
import java.util.Optional;

public interface MemoRepository {

    Memo save(Memo memo);
    Optional<Memo> findById(Long id);
    List<Memo> findAll();
    Memo updateContent(Long id, String content);
    void deleteById(Memo memo);
}
