package com.pageload.tasks.pageload;

import com.pageload.config.selenium.ChromeOptionsConfig;
import com.pageload.utils.FileUtils;
import com.pageload.utils.PageLoadUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class PageLoadTask {
    private static final Logger _logger = Logger.getLogger(PageLoadTask.class.getName());

    public static void executePageLoad() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter Base URL: ");
            String urlBase = scanner.nextLine();

            System.out.println("Enter page paths (separated by commas): ");
            String paths = scanner.nextLine();

            WebDriver driver = new ChromeDriver(ChromeOptionsConfig.prepareChromeOptions());

            List<List<Long>> pageLoadTimes = new ArrayList<>();

            for (String path : paths.split(",")) {
                _logger.info(String.format("Running pageload test for path: %s...", path));

                String url = urlBase + path.trim();

                List<Long> pageLoadTimesList = new ArrayList<>();

                for (int i = 0; i < 5; i++) {
                    long loadTime = PageLoadUtils.performPageLoadTest(driver, url);
                    _logger.info(String.format("%s. %s milliseconds", i + 1, loadTime));
                    pageLoadTimesList.add(loadTime);
                }

                pageLoadTimes.add(pageLoadTimesList);
            }

            FileUtils.writeResultsToFile(paths, pageLoadTimes);

            driver.quit();

        } catch (IOException | IllegalArgumentException ex) {
            _logger.severe("An unexpected error occurred: " + ex.getMessage());
        }
    }
}
