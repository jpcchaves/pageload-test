package com.pageload.utils;

import org.openqa.selenium.WebDriver;

public class PageLoadUtils {
    public static long performPageLoadTest(WebDriver driver,
                                           String url) {
        long startTime = System.currentTimeMillis();
        driver.get(url);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
