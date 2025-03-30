package com.fabiokusaba.encurtador_url.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fabiokusaba.encurtador_url.model.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {

    // Método customizado para buscar uma URL pela propriedade shortUrl.
    Optional<Url> findByShortUrl(String shortUrl);
}
