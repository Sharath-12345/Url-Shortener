package com.example.urlshortener.scheduler;

import com.example.urlshortener.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ClickSyncScheduler {

    private final StringRedisTemplate redisTemplate;
    private final ShortUrlRepository shortUrlRepository;

    // Runs every 60 seconds
    @Scheduled(fixedRate = 120000)
    public void syncClicksFromRedisToDb() {

        Set<String> keys = redisTemplate.keys("short:click:*");

        if (keys == null || keys.isEmpty()) {
            return;
        }

        for (String key : keys) {

            String shortCode = key.replace("short:click:", "");
            String value = redisTemplate.opsForValue().get(key);

            if (value == null) continue;

            long clickCount = Long.parseLong(value);

            // Update DB
            shortUrlRepository.addClicks(shortCode, clickCount);

            // Clear Redis count after sync
            redisTemplate.delete(key);
        }
    }
}
