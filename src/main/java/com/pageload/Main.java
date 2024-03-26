package com.pageload;

import com.pageload.config.selenium.ChromeOptionsConfig;
import com.pageload.utils.CalculationUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
  private static final Logger _logger = Logger.getLogger(Main.class.getName());
  private static final File resultsDir = new File("results");
  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      print("Enter Base URL: ");
      String urlBase = scanner.nextLine();

      print("Enter page paths (separated by commas): ");
      String paths = scanner.nextLine();

      WebDriver driver = new ChromeDriver(ChromeOptionsConfig.prepareChromeOptions());

      List<List<Long>> pageLoadTimes = new ArrayList<>();

      for (String path : paths.split(",")) {
        _logger.info(String.format("Running pageload test for path: %s...", path));

        String url = urlBase + path.trim();

        List<Long> pageLoadTimesList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
          long loadTime = performPageLoadTest(driver, url);
          pageLoadTimesList.add(loadTime);
        }

        pageLoadTimes.add(pageLoadTimesList);
      }

      writeResultsToFile(paths, pageLoadTimes);

      driver.quit();

    } catch (IOException | IllegalArgumentException ex) {
      _logger.severe("An unexpected error occurred: " + ex.getMessage());
    }
  }

  private static long performPageLoadTest(WebDriver driver, String url) {
    long startTime = System.currentTimeMillis();
    driver.get(url);
    long endTime = System.currentTimeMillis();
    return endTime - startTime;
  }

  private static void writeResultsToFile(String paths, List<List<Long>> pageLoadTimes) throws IOException {
    String filename = "page_load_times_" + sdf.format(new Date()) + ".txt";
    File file = new File(resultsDir, filename);

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
      String[] pathArray = paths.split(",");
      for (int i = 0; i < pageLoadTimes.size(); i++) {
        bw.write("Path: " + pathArray[i].trim() + "\n");

        for (int j = 0; j < pageLoadTimes.get(i).size(); j++) {
          bw.write("\n" + (j + 1) + ". " + pageLoadTimes.get(i).get(j) + " ms");
        }

        bw.write("\n\n");

        long averageTime = CalculationUtils.calculateAveragePageLoad(pageLoadTimes.get(i));
        bw.write("Average: " + averageTime + " ms\n\n");
      }
    }
  }

  private static void print(String message) {
    System.out.println(message);
  }
}
