package com.urlshortener.service;

import java.security.SecureRandom;

public class RandomKeyGenerator {
    static final String possibilities = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String generate(int length) {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(possibilities.charAt(secureRandom.nextInt(possibilities.length())));
        }
        return stringBuilder.toString();
    }
}
