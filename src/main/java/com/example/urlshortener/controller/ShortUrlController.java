package com.example.urlshortener.controller;

import com.example.urlshortener.config.ApiConfig;
import com.example.urlshortener.dto.CreateShortUrlRequest;
import com.example.urlshortener.dto.CreateShortUrlResponse;
import com.example.urlshortener.entity.ShortUrlEntity;
import com.example.urlshortener.service.ShortUrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    @PostMapping("/api/urls")
    public ResponseEntity<CreateShortUrlResponse> createShortUrl(
            @Valid @RequestBody CreateShortUrlRequest request) {

        ShortUrlEntity entity =
                shortUrlService.createShortUrl(request.getLongUrl(), null);

        String shortUrl = ApiConfig.baseUrl + entity.getShortCode();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateShortUrlResponse(shortUrl));
    }

    @GetMapping("/{shortCode:^(?!api|health|static|index\\.html).+}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {

        ShortUrlEntity entity = shortUrlService.getByShortCode(shortCode);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(entity.getLongUrl()))
                .build();
    }
}
