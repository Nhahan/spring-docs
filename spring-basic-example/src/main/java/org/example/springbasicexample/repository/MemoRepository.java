package org.example.springbasicexample.repository;

import org.example.springbasicexample.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}
