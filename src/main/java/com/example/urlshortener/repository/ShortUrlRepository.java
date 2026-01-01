package com.example.urlshortener.repository;

import com.example.urlshortener.entity.ShortUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrlEntity, Long> {

    Optional<ShortUrlEntity> findByShortCode(String shortCode);

    boolean existsByShortCode(String shortCode);

    @Transactional
    @Modifying
    @Query("""
        UPDATE ShortUrlEntity s
        SET s.clickCount = s.clickCount + :count
        WHERE s.shortCode = :shortCode
    """)
    void addClicks(String shortCode, long count);

    @Transactional
    @Modifying
    @Query("""
        UPDATE ShortUrlEntity s
        SET s.isActive = false
        WHERE s.expiresAt IS NOT NULL
          AND s.expiresAt < :now
    """)
    void disableExpired(LocalDateTime now);
}
