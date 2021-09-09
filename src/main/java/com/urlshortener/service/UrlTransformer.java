package com.urlshortener.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UrlTransformer {
    static String HTTP = "http://";
    static String HTTPS = "https://";
    static String FTP = "ftp://";

    public String transform(String url) {
        url = removeExtraLeadingAndTrailingSlash(url);
        if (!isStartingSchema(url)) {
            url = HTTP + url;
        }
        return url;
    }

    private String removeExtraLeadingAndTrailingSlash(String url) {
        boolean endWithSlash = url.charAt(url.length()-1) == '/';
        url = StringUtils.stripStart(url, "/");
        url = StringUtils.stripEnd(url, "/");
        if (endWithSlash) url += '/';

        return url;
    }

    private boolean isStartingSchema(String url) {
        url = url.toLowerCase();
        return url.startsWith(HTTP) || url.startsWith(HTTPS) || url.startsWith(FTP);
    }
}
