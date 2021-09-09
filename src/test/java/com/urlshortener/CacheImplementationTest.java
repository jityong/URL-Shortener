package com.urlshortener;

import com.urlshortener.UrlShortenerApplication;
import com.urlshortener.model.UrlRepository;
import com.urlshortener.model.UrlShortener;
import com.urlshortener.service.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UrlShortenerApplication.class)
public class CacheImplementationTest {

	@Autowired
	CacheManager cacheManager;

	@Autowired
	UrlRepository urlRepository;

	@Autowired
	UrlService urlService;

	@BeforeEach
	@Transactional
	void setUp() {
		// reserve this key for test
		String key = "urlKey1";
		// ideally should set up something like a H2 in memory DB for testing purposes
		if (Objects.isNull(urlRepository.findByUrlKey(key))) {
			urlRepository.save(new UrlShortener(key, "fullUrl1"));
		}
		urlService.getUrlFromKey(key);
	}

	private String getCachedUrl(String urlKey) {
		return cacheManager.getCache("urls").get(urlKey, String.class);
	}

	@Test
	void test_UrlShouldBeCached() {
        String key = "urlKey1";
        String fullUrl = urlRepository.findByUrlKey(key);
        assertEquals(fullUrl, getCachedUrl(key));
	}

	@Test
	void test_UrlNotInCached() {
		String key = "urlKey3";
		assertEquals(null, getCachedUrl(key));
	}

}
