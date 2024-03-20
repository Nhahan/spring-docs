package org.example.concurrency;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OptimisticCounterRepository extends JpaRepository<OptimisticCounter, Long> {
}
