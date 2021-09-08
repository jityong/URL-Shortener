package com.urlshortener.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UrlRepository extends CrudRepository<UrlShortener, Long> {
    @Query(value="SELECT full_url FROM urlshortener WHERE urlshortener.url_key = ?1", nativeQuery=true)
    String findByUrlKey(String urlKey);
}
