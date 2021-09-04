package com.urlshortener.model;

public class UrlResponse {
    private String fullUrl;
    private String shortenedUrl;

    public UrlResponse(String fullUrl, String shortenedUrl) {
        this.fullUrl = fullUrl;
        this.shortenedUrl = shortenedUrl;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getShortenedUrl() {
        return shortenedUrl;
    }

    public void setShortenedUrl(String shortenedUrl) {
        this.shortenedUrl = shortenedUrl;
    }
}
