package com.example.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateShortUrlResponse {
    private String shortUrl;
}
