package com.fundoonotes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    // Save data with expiry
    public void save(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(
                key,
                value,
                timeout,
                java.util.concurrent.TimeUnit.MINUTES
        );
    }

    // Get data
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // Delete
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}