package org.example.concurrency;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Counter {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int count;

    public Counter(int count) {
        this.count = count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
