package org.example.concurrency;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class CounterServiceTest {

    @Autowired
    private CounterService counterService;

    @BeforeEach
    void setUp() {
        counterService.save(new Counter(100));
        counterService.initializeCounter();
    }

    @AfterEach
    void tearDown() {
        counterService.reset();
    }

    @Test @DisplayName("RDBMS를 사용했을 시 동시성 문제가 발생")
    void concurrencyTest() {
        System.out.println("\n\n\n\n[concurrencyTest]");
        IntStream.range(0, 100).parallel().forEach(i -> counterService.decreaseCount());
        counterService.printCount();
    }

    @Test @DisplayName("Redis의 Lock을 이용한 동시성 제어")
    void concurrencyTestUsingLock() {
        System.out.println("\n\n\n\n[concurrencyTestUsingLock]");
        IntStream.range(0, 100).parallel().forEach(i -> counterService.decreaseCountUsingLock());
        counterService.printCount();
    }

    @Test @DisplayName("Redis의 싱글 스레드 특성을 이용한 동시성 제어")
    void concurrencyTestWithRedis() {
        System.out.println("\n\n\n\n[concurrencyTestNoLockWithRedis]");
        IntStream.range(0, 100).parallel().forEach(i -> counterService.decrementCounter());
        counterService.getCounter();
    }
}
