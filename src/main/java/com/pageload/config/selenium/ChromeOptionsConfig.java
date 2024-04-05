package com.pageload.config.selenium;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ChromeOptionsConfig {
    public static ChromeOptions prepareChromeOptions() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("applicationCacheEnabled", false);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        chromeOptions.addArguments("--headless=new");
        chromeOptions.addArguments("--disable-cache");
        return chromeOptions;
    }
}
