package br.com.gmalheiro.urlshortener.service;

import br.com.gmalheiro.urlshortener.controller.dto.ShortenUrlRequest;
import br.com.gmalheiro.urlshortener.entity.UrlEntity;
import br.com.gmalheiro.urlshortener.repository.UrlRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService (UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenUrl (ShortenUrlRequest request, HttpServletRequest servletRequest) {
        String id;
        do {
            id = RandomStringUtils.randomAlphanumeric(5,10);
        } while (urlRepository.existsById(id));
        urlRepository.save(new UrlEntity(id,request.url(), LocalDateTime.now().plusMinutes(1)));
        return servletRequest.getRequestURL().toString().replace("shorten-url",id);
    }

    @Cacheable("urls")
    private UrlEntity findUrlById (String id) {
        List<UrlEntity> urls = urlRepository.findAll();
        Optional<UrlEntity> urlOptional = urls.stream()
                .filter(url -> url.getId().equals(id))
                .findFirst();
        return urlOptional.orElse(null);
    }


    public HttpHeaders redirect (String id) {
        UrlEntity url = findUrlById(id);

        if (Objects.isNull(url)){
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url.getFullUrl()));
        return headers;
    }


}
