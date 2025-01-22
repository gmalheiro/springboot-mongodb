package br.com.gmalheiro.urlshortener.controller;

import br.com.gmalheiro.urlshortener.controller.dto.ShortenUrlRequest;
import br.com.gmalheiro.urlshortener.controller.dto.ShortenUrlResponse;
import br.com.gmalheiro.urlshortener.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class UrlController {
    private final UrlService urlService;

    public UrlController (UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten-url")
    public ResponseEntity<ShortenUrlResponse> shortenUrl (@RequestBody ShortenUrlRequest request, HttpServletRequest servletRequest) {
        return ResponseEntity.ok(new ShortenUrlResponse(urlService.shortenUrl(request,servletRequest)));
    }

    @GetMapping("{id}")
    public ResponseEntity<Void> redirect (@PathVariable("id") String id) {
        HttpHeaders headers = urlService.redirect(id);
        if (Objects.isNull(headers))
            return ResponseEntity.notFound().build();
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }

}
