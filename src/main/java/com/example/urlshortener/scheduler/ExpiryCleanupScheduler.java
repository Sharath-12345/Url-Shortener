package com.example.urlshortener.scheduler;

import com.example.urlshortener.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ExpiryCleanupScheduler {

    private final ShortUrlRepository shortUrlRepository;

    // Runs every 5 minutes
    @Scheduled(fixedRate = 300000)
    public void disableExpiredUrls() {
        shortUrlRepository.disableExpired(LocalDateTime.now());
    }
}
