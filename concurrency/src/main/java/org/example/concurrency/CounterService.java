package org.example.concurrency;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CounterService {

    private final CounterRepository counterRepository;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;

    private static final String LOCK_KEY = "counterLock";

    @Transactional
    public void save(Counter counter) {
        counterRepository.save(counter);
    }

    @Transactional
    public void reset() {
        Counter counter = counterRepository.findById(1L).orElseThrow();
        counter.setCount(100);
        counterRepository.save(counter);
    }

    @Transactional
    public void decreaseCount() {
        Counter counter = counterRepository.findById(1L).orElseThrow();
        counter.setCount(counter.getCount() - 1);
        counterRepository.save(counter);
    }

    public void decreaseCountUsingLock() {
        RLock lock = redissonClient.getFairLock(LOCK_KEY);
        try {
            boolean isLocked = lock.tryLock(10, 60, TimeUnit.SECONDS);
            if (isLocked) {
                try {
                    Counter counter = counterRepository.findById(1L).orElseThrow();
                    counter.setCount(counter.getCount() - 1);
                    counterRepository.save(counter);
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void printCount() {
        System.out.println(counterRepository.findById(1L).orElseThrow().getCount());
    }

    public void initializeCounter() {
        ValueOperations<String, String> ops = this.stringRedisTemplate.opsForValue();
        ops.set("counter", "100");
    }

    public void decrementCounter() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.decrement("counter");
    }

    public void getCounter() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String value = ops.get("counter");
        System.out.println(value);
    }
}
