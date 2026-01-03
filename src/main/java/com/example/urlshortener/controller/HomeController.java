package com.example.urlshortener.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @Value("${server.port:8080}")
    private String serverPort;

    @GetMapping("/api/info")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "running");
        response.put("service", "URL Shortener API");
        response.put("version", "1.0.0");

        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("Create Short URL", "POST /api/urls");
        endpoints.put("Redirect", "GET /{shortCode}");

        response.put("endpoints", endpoints);

        Map<String, Object> example = new HashMap<>();
        example.put("method", "POST");
        example.put("url", "/api/urls");
        example.put("body", Map.of("longUrl", "https://example.com"));

        response.put("example", example);

        return response;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Service is healthy");
        return response;
    }
}