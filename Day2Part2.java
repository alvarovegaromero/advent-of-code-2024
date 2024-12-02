// Day 2  - Part 2
// https://adventofcode.com/2024/day/2#part2

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day2Part2 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int safeReports = 0;
    List<Integer> row;

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line.trim().isEmpty())
        break;

      String[] numbers = line.split("\\s+");
      row = new ArrayList<Integer>();

      for (String num : numbers) {
        row.add(Integer.parseInt(num));
      }

      if (checkSafeReport(row)) { // Process row and update safeReport counter if report is safe
        safeReports++;
      }
    }

    scanner.close();
    System.out.println("Total number of safe reports: " + safeReports);
  }

  public static boolean checkSafeReport(List<Integer> row) {
    // Check if the report is initially safe without any removal
    if (isSafe(row)) {
      return true;
    }

    // Try removing each element and check if the report becomes safe
    // Not the most efficient solution, but good enought for the time dedicated to this problem 
    // and low input size of each row - A Better aproach would be to try in the array but "skipping" elements
    List<Integer> modifiedRow;
    for (int i = 0; i < row.size(); i++) {
      modifiedRow = new ArrayList<Integer>(row);
      modifiedRow.remove(i); // Remove the element at index i

      if (isSafe(modifiedRow)) {
        return true;
      }
    }

    // If no modification makes the report safe, return false
    return false;
  }

  public static boolean isSafe(List<Integer> row) {
    boolean isAscending = row.get(1) > row.get(0); // Check if row is ascending or descending initially
    int diff;
    final int MIN_DIFF = 1;
    final int MAX_DIFF = 3;

    for (int i = 1; i < row.size(); i++) {
      diff = Math.abs(row.get(i) - row.get(i - 1));

      // Distance among elements are between 1 and 3. If not, report not safe
      if (diff < MIN_DIFF || diff > MAX_DIFF) {
        return false;
      }

      // If row doesn't follow the correct order, report not safe
      if ((isAscending && row.get(i) < row.get(i - 1)) || (!isAscending && row.get(i) > row.get(i - 1))) {
        return false;
      }
    }

    return true; // If all checks pass, report is safe
  }
}