package com.urlshortener.service;

import com.urlshortener.model.UrlShortener;
import com.urlshortener.model.UrlRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UrlService {
    UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Transactional
    public boolean insertURL(String key, String url) {
        if (Objects.isNull(urlRepository.findByUrlKey(key))) {
            urlRepository.save(new UrlShortener(key, url));
            return true;
        }
        return false;
    }

    @Cacheable(value="urls", key="#urlKey")
    public String getUrlFromKey(String urlKey) {
        return urlRepository.findByUrlKey(urlKey);
    }
}
