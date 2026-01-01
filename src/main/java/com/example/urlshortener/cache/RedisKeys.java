package com.example.urlshortener.cache;



public final class RedisKeys {

    private RedisKeys() {}

    public static String shortUrlKey(String shortCode) {
        return "short:url:" + shortCode;
    }

    public static String clickCountKey(String shortCode) {
        return "short:click:" + shortCode;
    }
}
