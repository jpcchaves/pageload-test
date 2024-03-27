package com.pageload.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class FileUtils {
    private static final File resultsDir = new File("results");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
    private static final Logger _logger = Logger.getLogger(FileUtils.class.getName());


    public static void writeResultsToFile(String paths, List<List<Long>> pageLoadTimes) throws IOException {
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
}
