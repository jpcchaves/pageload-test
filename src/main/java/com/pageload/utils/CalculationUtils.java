package com.pageload.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalculationUtils {
  public static long calculateAveragePageLoad(List<Long> pageLoadList) {
    List<Long> pageLoadListCopy = new ArrayList<>(List.copyOf(pageLoadList));
    Collections.sort(pageLoadListCopy);

    //remove max and min values
    pageLoadListCopy.remove(0);
    pageLoadListCopy.remove(pageLoadListCopy.size() - 1);

    long totalTime = 0;

    for (Long tempo : pageLoadListCopy) {
      totalTime += tempo;
    }

    return totalTime / pageLoadListCopy.size();
  }
}
