package org.example.concurrency;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class OptimisticCounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int count;

    @Version
    private int version;

    public OptimisticCounter(int count) {
        this.count = count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
