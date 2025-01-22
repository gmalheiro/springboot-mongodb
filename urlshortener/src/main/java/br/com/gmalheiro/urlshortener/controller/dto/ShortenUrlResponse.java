package br.com.gmalheiro.urlshortener.controller.dto;

public record ShortenUrlResponse(String url) {
    @Override
    public String toString() {
        return url;
    }
}
