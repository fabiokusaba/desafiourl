package com.fabiokusaba.encurtador_url.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.fabiokusaba.encurtador_url.model.Url;
import com.fabiokusaba.encurtador_url.repository.UrlRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    public String shortenUrl(String originalUrl) {
        String shortUrl = generateShortUrl();

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        url.setExpirationDate(LocalDateTime.now().plusDays(30)); // Prazo de expiração de 30 dias.

        urlRepository.save(url);

        return shortUrl;
    }

    public Optional<Url> getOriginalUrl(String shortUrl) {
        Optional<Url> urlOptional = urlRepository.findByShortUrl(shortUrl);

        // Verificação se está presente no banco
        if (urlOptional.isPresent()) {
            // Uma vez presente pego os dados
            Url url = urlOptional.get();

            // Faço a verificação do tempo de expiração
            if (url.getExpirationDate().isAfter(LocalDateTime.now())) {
                // Se não expirou retorno a url
                return Optional.of(url);
            } else {
                // Se expirou removo do banco
                urlRepository.delete(url);
            }
        }

        // Se não encontrou retorno vazio
        return Optional.empty();
    }

    private String generateShortUrl() {
        // Cadeia de caracteres
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        // Variável randômica para tamanho
        int length = 5 + random.nextInt(6);

        // Loop, adicionando (append) ao meu StringBuilder da minha cadeia de caracteres um caracter aleatório.
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }
}
