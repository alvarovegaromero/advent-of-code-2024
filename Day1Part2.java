// Day 1 - Part 2 
// https://adventofcode.com/2024/day/1#part2

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Day1Part2 {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    List<Integer> list1 = new ArrayList<Integer>();
    Map<Integer, Integer> map2 = new HashMap<Integer, Integer>();

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line.trim().isEmpty())
        break;

      String[] numbers = line.split("\\s+");
      if (numbers.length == 2) {
        list1.add(Integer.parseInt(numbers[0]));

        updateMap(map2, Integer.parseInt(numbers[1]));
      }
    }
    scanner.close();

    int totalSum = 0;
    for (int i = 0; i < list1.size(); i++) {
      totalSum += list1.get(i) * map2.getOrDefault(list1.get(i), 0);
    }

    System.out.println("Total sum: " + totalSum);
  }

  public static void updateMap(Map<Integer, Integer> map, int num) {
    if (map.containsKey(num)) {
      map.put(num, map.get(num) + 1);
    } else {
      map.put(num, 1);
    }
  }
}
