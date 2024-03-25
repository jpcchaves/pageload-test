package com.pageload.config.selenium;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeOptionsConfig {
    public static ChromeOptions prepareChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return chromeOptions;
    }
}
