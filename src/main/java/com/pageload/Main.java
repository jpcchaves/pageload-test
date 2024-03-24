package com.pageload;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
  private static final Logger _logger = Logger.getLogger(Main.class.getName());

  public static void main(String[] args) throws IOException {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Enter Base URL: ");
    String urlBase = scanner.nextLine();

    System.out.println("Enter page paths (separated by commas): ");
    String paths = scanner.nextLine();

    WebDriver driver = new ChromeDriver();

    List<List<Long>> pageLoadTimes = new ArrayList<>();

    for (String path : paths.split(",")) {
      _logger.info("Running pageload test for path: %s...".formatted(path));

      String url = urlBase + path;

      List<Long> pageLoadTimesList = new ArrayList<>();
      for (int i = 0; i < 5; i++) {
        long startTime  = System.currentTimeMillis();
        driver.get(url);
        long endTime  = System.currentTimeMillis();

        long loadTime = endTime  - startTime ;

        pageLoadTimesList.add(loadTime);
      }

      pageLoadTimes.add(pageLoadTimesList);
    }

    String filename = "page_load_times_" + sdf.format(new java.util.Date()) + ".txt";
    File file = new File(filename);

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
      for (int i = 0; i < pageLoadTimes.size(); i++) {
        bw.write("Path: " + paths.split(",")[i] + "\n");
        for (int j = 0; j < pageLoadTimes.get(i).size(); j++) {

          bw.write("\n" + (j + 1) + ". " + pageLoadTimes.get(i).get(j) + " ms");
        }
        bw.write("\n\n");

        long totalTime = 0;
        for (Long tempo : pageLoadTimes.get(i)) {
          totalTime += tempo;
        }
        long averageTime = totalTime / pageLoadTimes.get(i).size();

        bw.write("Average: " + averageTime + " ms\n");
        bw.write("\n");
      }
    }

    driver.quit();
  }
}