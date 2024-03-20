package org.example.concurrency;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@SpringBootTest
public class OptimisticCounterServiceTest {

    @Autowired
    private OptimisticCounterService optimisticCounterService;

    @BeforeEach
    void setUp() {
        optimisticCounterService.save(new OptimisticCounter(100));
    }

    @AfterEach
    void tearDown() {
        optimisticCounterService.reset();
    }

    @Test @DisplayName("낙관적 락을 이용한 동시성 제어")
    void concurrencyTestWithOptimisticLock() {
        System.out.println("\n\n\n\n[concurrencyTestWithOptimisticLock]");
        AtomicInteger optimisticLockFailures = new AtomicInteger(0);

        IntStream.range(0, 100).parallel().forEach(i -> {
            try {
                optimisticCounterService.decreaseCount();
            } catch (ObjectOptimisticLockingFailureException e) {
                optimisticLockFailures.incrementAndGet();
            }
        });

        optimisticCounterService.printCount();
        System.out.println("낙관적 락 에러 횟수: " + optimisticLockFailures.get());
    }

    @Test @DisplayName("낙관적 락을 이용한 동시성 제어 및 재시도 로직")
    void concurrencyTestWithOptimisticLockAndRetry() {
        System.out.println("\n\n\n\n[concurrencyTestWithOptimisticLockAndRetry]");
        final int maxRetries = 5; // 최대 재시도 횟수
        AtomicInteger totalAttempts = new AtomicInteger(0);
        AtomicInteger successfulDecrements = new AtomicInteger(0);

        IntStream.range(0, 100).parallel().forEach(i -> {
            boolean updated = false;
            int attempts = 0;
            while (!updated && attempts < maxRetries) {
                try {
                    optimisticCounterService.decreaseCount();
                    successfulDecrements.incrementAndGet();
                    updated = true;
                } catch (ObjectOptimisticLockingFailureException e) {
                    attempts++;
                }
                totalAttempts.incrementAndGet();
            }
            if (!updated) {
                System.out.println("최대 재시도를 " + attempts + "회 하였으나 실패. (for iteration " + i + "번째에서 발생.)");
            }
        });

        optimisticCounterService.printCount();
        System.out.println("성공적으로 감소된 횟수: " + successfulDecrements.get());
        System.out.println("총 시도 횟수: " + totalAttempts.get());
    }
}
