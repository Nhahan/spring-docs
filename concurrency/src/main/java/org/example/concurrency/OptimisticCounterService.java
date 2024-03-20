package org.example.concurrency;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptimisticCounterService {

    private final OptimisticCounterRepository optimisticCounterRepository;

    @Transactional
    public void save(OptimisticCounter optimisticCounter) {
        optimisticCounterRepository.save(optimisticCounter);
    }

    @Transactional
    public void reset() {
        OptimisticCounter optimisticCounter = optimisticCounterRepository.findById(1L).orElseThrow();
        optimisticCounter.setCount(100);
        optimisticCounterRepository.save(optimisticCounter);
    }

    @Transactional
    public void decreaseCount() {
        OptimisticCounter optimisticCounter = optimisticCounterRepository.findById(1L).orElseThrow();
        optimisticCounter.setCount(optimisticCounter.getCount() - 1);
        optimisticCounterRepository.save(optimisticCounter);
    }

    public void printCount() {
        System.out.println(optimisticCounterRepository.findById(1L).orElseThrow().getCount());
    }
}
