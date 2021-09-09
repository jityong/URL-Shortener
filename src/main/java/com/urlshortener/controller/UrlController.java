package com.urlshortener.controller;

import com.urlshortener.model.UrlRequest;
import com.urlshortener.model.UrlResponse;
import com.urlshortener.service.RandomKeyGenerator;
import com.urlshortener.service.UrlService;
import com.urlshortener.service.TransformUrl;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Objects;

@Controller
public class UrlController {
    Logger logger = LoggerFactory.getLogger(UrlController.class);

    private static String server;
    UrlService urlService;

    @Autowired
    UrlController(@Value("${server.url}") String server,
                  UrlService urlService) {
        this.server = server;
        this.urlService = urlService;
    }

    // query DB for actual url
    // check if exists - if yes then setUrl to retrieved URL. if not, redirect back to homepage
    @GetMapping("/{key}")
    public RedirectView getActualURL(@PathVariable String key, RedirectAttributes redir) {
        RedirectView redirectView = new RedirectView("/", true);
        String url = urlService.getUrlFromKey(key);
        if (Objects.nonNull(url)) {
            redirectView.setUrl(url);
        } else {
            redir.addAttribute("redirected", true);
        }
        return redirectView;
    }

    @GetMapping("/")
    public String generateUrl(@RequestParam(value = "redirected", defaultValue = "false", required = false) boolean redirected,
                              Model model) {
        model.addAttribute("isRedirected", redirected);
        model.addAttribute("urlRequest", new UrlRequest());
        return "generateUrl";
    }

    // get the full form of the URL - ie with http:// or https://
    // validate URL
    @PostMapping("/")
    public String generateUrl(@ModelAttribute UrlRequest urlRequest, Model model) {
        model.addAttribute("errorMessage", "");
        model.addAttribute("previousUrlRequest", urlRequest);
        String url = urlRequest.getUrl();
        url = TransformUrl.transform(url);
        UrlValidator urlValidator = new UrlValidator();
        if (!urlValidator.isValid(url)) {
            model.addAttribute("errorMessage", "invalid Url");
        }
        String key = RandomKeyGenerator.generate(6);
        // ensure atomic and correctness
        while (!urlService.insertURL(key, url)) {
            key = RandomKeyGenerator.generate(6);
        }
        UrlResponse response = new UrlResponse(url, server + key);
        model.addAttribute("urlRequest", new UrlRequest());
        model.addAttribute("urlResponse", response);

        logger.info("Generated URL. Shortened Url: " + response.getShortenedUrl() + "Full URL: " + response.getFullUrl());
        return "generateUrlResponse";
    }
}


