package com.example.urlshortener.repository;

import com.example.urlshortener.entity.UrlClickEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlClickRepository extends JpaRepository<UrlClickEntity, Long> {
}