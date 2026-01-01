package com.example.urlshortener.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    public void set(String key, String value, long ttlSeconds) {
        redisTemplate.opsForValue()
                .set(key, value, Duration.ofSeconds(ttlSeconds));
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    public void increment(String key) {
        redisTemplate.opsForValue().increment(key);
    }
}
