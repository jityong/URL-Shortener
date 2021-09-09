package com.urlshortener.service;

import org.apache.commons.lang3.StringUtils;

public class TransformUrl {
    static String HTTP = "http://";
    static String HTTPS = "https://";
    static String FTP = "ftp://";

    public static String transform(String url) {
        url = removeExtraLeadingAndTrailingSlash(url);
        if (!isStartingSchema(url)) {
            url = HTTP + url;
        }
        return url;
    }

    private static String removeExtraLeadingAndTrailingSlash(String url) {
        boolean endWithSlash = url.charAt(url.length()-1) == '/';
        url = StringUtils.stripStart(url, "/");
        url = StringUtils.stripEnd(url, "/");
        if (endWithSlash) url += '/';

        return url;
    }

    private static boolean isStartingSchema(String url) {
        url = url.toLowerCase();
        return url.startsWith(HTTP) || url.startsWith(HTTPS) || url.startsWith(FTP);
    }
}
