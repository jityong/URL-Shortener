package com.urlshortener.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "urlshortener",
        uniqueConstraints = @UniqueConstraint(columnNames = "urlKey"),
        indexes = @Index(columnList = "urlKey"))
public class UrlShortener {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String urlKey;
    private String fullUrl;

    public UrlShortener() {
    }

    public UrlShortener(String urlKey, String fullUrl) {
        this.urlKey = urlKey;
        this.fullUrl = fullUrl;
    }

    public Long getId() {
        return id;
    }

    public String getUrlKey() {
        return urlKey;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    public void setFullUrl(String url) {
        this.fullUrl = url;
    }
}

