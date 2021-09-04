package com.urlshortener.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "url",
        uniqueConstraints = @UniqueConstraint(columnNames = "urlKey"),
        indexes = @Index(columnList = "urlKey"))
public class Url {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String urlKey;
    private String url;

    public Url() {
    }

    public Url(String urlKey, String url) {
        this.urlKey = urlKey;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public String getUrlKey() {
        return urlKey;
    }

    public String getUrl() {
        return url;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

