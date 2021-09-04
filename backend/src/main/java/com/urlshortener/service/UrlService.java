package com.urlshortener.service;

import com.urlshortener.model.Url;
import com.urlshortener.model.UrlRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
public class UrlService {
    UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Transactional
    public boolean insertURL(String key, String url) {
        if (Objects.isNull(urlRepository.findByUrlKey(key))) {
            urlRepository.save(new Url(key, url));
            return true;
        }
        return false;
    }

    public String getUrlFromKey(String key) {
        return urlRepository.findByUrlKey(key);
    }
}
