package org.example.cache;

import java.util.HashMap;
import java.util.Map;

public class SimpleCache {

    private final Map<String, Integer> cache = new HashMap<>();

    public void increment(String key) {
        int count = cache.getOrDefault(key, 0);
        cache.put(key, count + 1);
    }

    public int getValue(String key) {
        return cache.getOrDefault(key, 0);
    }
}
