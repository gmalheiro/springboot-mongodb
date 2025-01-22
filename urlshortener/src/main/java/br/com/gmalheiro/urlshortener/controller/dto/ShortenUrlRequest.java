package br.com.gmalheiro.urlshortener.controller.dto;

public record ShortenUrlRequest(String url) {
    @Override
    public String toString() {
        return url;
    }
}
