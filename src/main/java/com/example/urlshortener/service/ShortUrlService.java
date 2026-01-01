package com.example.urlshortener.service;

import com.example.urlshortener.cache.RedisConstants;
import com.example.urlshortener.cache.RedisKeys;
import com.example.urlshortener.cache.RedisService;
import com.example.urlshortener.entity.ShortUrlEntity;
import com.example.urlshortener.exception.UrlExpiredException;
import com.example.urlshortener.exception.UrlNotFoundException;
import com.example.urlshortener.repository.ShortUrlRepository;
import com.example.urlshortener.util.Base62Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final RedisService redisService;

    @Transactional
    public ShortUrlEntity createShortUrl(String longUrl, LocalDateTime expiresAt) {

        ShortUrlEntity entity = ShortUrlEntity.builder()
                .longUrl(longUrl)
                .createdAt(LocalDateTime.now())
                .expiresAt(expiresAt)
                .isActive(true)
                .clickCount(0L)
                .build();

        entity = shortUrlRepository.save(entity);

        String shortCode = Base62Util.encode(entity.getId());
        entity.setShortCode(shortCode);

        entity = shortUrlRepository.save(entity);

        redisService.set(
                RedisKeys.shortUrlKey(shortCode),
                entity.getLongUrl(),
                RedisConstants.DEFAULT_TTL_SECONDS
        );

        return entity;
    }

    @Transactional
    public ShortUrlEntity getByShortCode(String shortCode) {

        String redisKey = RedisKeys.shortUrlKey(shortCode);

        String longUrl = redisService.get(redisKey).orElse(null);

        ShortUrlEntity entity;

        if (longUrl != null) {
            redisService.increment(RedisKeys.clickCountKey(shortCode));

            entity = shortUrlRepository.findByShortCode(shortCode)
                    .orElseThrow(() -> new UrlNotFoundException("Short URL not found"));

            return entity;
        }

        entity = shortUrlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException("Short URL not found"));

        if (!entity.getIsActive()) {
            throw new UrlNotFoundException("Short URL is disabled");
        }

        if (entity.isExpired()) {
            entity.setIsActive(false);
            shortUrlRepository.save(entity);
            throw new UrlExpiredException("Short URL has expired");
        }

        redisService.set(
                redisKey,
                entity.getLongUrl(),
                RedisConstants.DEFAULT_TTL_SECONDS
        );

        redisService.increment(RedisKeys.clickCountKey(shortCode));
        return entity;
    }
}
