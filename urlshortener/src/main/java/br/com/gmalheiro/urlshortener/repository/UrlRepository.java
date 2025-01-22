package br.com.gmalheiro.urlshortener.repository;

import br.com.gmalheiro.urlshortener.entity.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<UrlEntity,String> {
}
