package org.example.testspringfirst.repository;

import org.example.testspringfirst.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}
