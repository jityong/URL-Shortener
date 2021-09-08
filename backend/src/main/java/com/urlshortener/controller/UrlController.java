package com.urlshortener.controller;

import com.urlshortener.model.UrlRequest;
import com.urlshortener.model.UrlResponse;
import com.urlshortener.service.RandomKeyGenerator;
import com.urlshortener.service.UrlService;
import com.urlshortener.service.TransformUrl;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;


import java.util.Objects;

@RestController
@CrossOrigin(origins ="http://localhost:3000")
public class UrlController {
    @Value("${backend.server.url}")
    private static String backend;
    @Value("${frontend.server.url}")
    private static String frontend;

    Logger logger = LoggerFactory.getLogger(UrlController.class);
    UrlService urlService;

    UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{key}")
    public RedirectView getActualURL(@PathVariable String key) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(frontend + "redirected");
        // query DB for actual url
        // check if exists - if yes then setUrl to retrieved URL. if not, redirect back to homepage
        String url = urlService.getUrlFromKey(key);
        if (Objects.nonNull(url)) {
            redirectView.setUrl(url);
        }
        return redirectView;
    }

    @PostMapping("/")
    public UrlResponse generateShortURL(@RequestBody UrlRequest urlRequest) {
        String url = urlRequest.getUrl();
        // get the full form of the URL - ie with http:// or https://
        url = TransformUrl.transform(url);
        // validate URL
        UrlValidator urlValidator = new UrlValidator();
        if (!urlValidator.isValid(url)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid URL");
        }
        String key = RandomKeyGenerator.generate(6);
        // we need it to be atomic
        while (!urlService.insertURL(key, url)) {
            key = RandomKeyGenerator.generate(6);
        }
        UrlResponse response = new UrlResponse(url, backend + key);
        logger.info("Generated URL. Shortened Url: " + response.getShortenedUrl() + "Full URL: " + response.getFullUrl());
        return response;
    }
}


