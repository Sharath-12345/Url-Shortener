package com.example.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateShortUrlRequest {

    @NotBlank(message = "Long URL is required")
    @Pattern(
            regexp = "^(http|https)://.*$",
            message = "URL must start with http:// or https://"
    )
    private String longUrl;
}
