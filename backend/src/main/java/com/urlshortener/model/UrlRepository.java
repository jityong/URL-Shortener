package com.urlshortener.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UrlRepository extends CrudRepository<Url, Long> {
    @Query(value="SELECT url from url WHERE url.url_key = ?1", nativeQuery=true)
    String findByUrlKey(String urlKey);
}
