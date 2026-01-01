package com.example.urlshortener.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "short_urls",
        indexes = {
                @Index(name = "idx_short_code", columnList = "short_code", unique = true),
                @Index(name = "idx_expires_at", columnList = "expires_at")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShortUrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_code", length = 20, unique = true)
    private String shortCode;

    @Column(name = "long_url", nullable = false, columnDefinition = "TEXT")
    private String longUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "click_count", nullable = false)
    private Long clickCount;

    public boolean isExpired() {
        return expiresAt != null && expiresAt.isBefore(LocalDateTime.now());
    }

}
