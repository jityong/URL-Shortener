package com.urlshortener.service;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class UrlTransformerTest {
    private UrlTransformer urlTransformer;

    @BeforeTest
    public void SetUp() {
        urlTransformer = new UrlTransformer();
    }

    @Test(dataProvider = "url-provider")
    public void test_transform(String url, String expected) {
        url = urlTransformer.transform(url);
        assertEquals(url, expected);
    }

    @DataProvider(name="url-provider")
    public Object[][] urlProvider() {
        return new Object[][] {
                {"www.google.com", "http://www.google.com"},
                {"http://www.google.com/", "http://www.google.com/"},
                {"http://www.google.com//", "http://www.google.com/"},
                {"/www.google.com//", "http://www.google.com/"},
                {"///www.google.com//", "http://www.google.com/"},
        };
    }
}
