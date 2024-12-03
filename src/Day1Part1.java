// Day 1 - Part 1
// https://adventofcode.com/2024/day/1

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.Collections;

public class Day1Part1 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    List<Integer> list1 = new ArrayList<Integer>();
    List<Integer> list2 = new ArrayList<Integer>();
    

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line.trim().isEmpty())
        break;

      String[] numbers = line.split("\\s+");
      if (numbers.length == 2) {
        list1.add(Integer.parseInt(numbers[0]));
        list2.add(Integer.parseInt(numbers[1]));
      }
    }
    scanner.close();

    Collections.sort(list1);
    Collections.sort(list2);

    int totalSum = 0;
    for (int i = 0; i < list1.size(); i++) {
      totalSum += Math.abs(list1.get(i) - list2.get(i));
    }

    System.out.println("Total sum: " + totalSum);
  }
}