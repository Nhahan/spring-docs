package org.example.concurrency;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    }

    @AfterEach
    void tearDown() {
        counterService.reset();
    }

    @Test
    void concurrencyTest() {
        System.out.println("\n\n\n\n[concurrencyTest]");
        IntStream.range(0, 100).parallel().forEach(i -> counterService.decreaseCount());
        counterService.printCount();
    }

    @Test
    void concurrencyTestUsingLock() {
        System.out.println("\n\n\n\n[concurrencyTestUsingLock]");
        IntStream.range(0, 100).parallel().forEach(i -> counterService.decreaseCountUsingLock());
        counterService.printCount();
    }
}
