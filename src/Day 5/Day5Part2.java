// Day 5 - Part 2
// https://adventofcode.com/2024/day/5#part2

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class Day5Part2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, List<Integer>> rules = new HashMap<>();
        List<List<Integer>> updates = new ArrayList<>();

        System.out.println("Enter rules and updates and then Ctrl+D to finish");

        boolean readingRules = true;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) {
                readingRules = false; // When we find an empty line, change to read updates
                continue;
            }

            if (readingRules) {
                String[] parts = line.split("\\|");
                int before = Integer.parseInt(parts[0].trim());
                int after = Integer.parseInt(parts[1].trim());
                rules.computeIfAbsent(before, k -> new ArrayList<>()).add(after); // Add the rule to the map
            } else {
                List<Integer> update = new ArrayList<>();
                for (String page : line.split(",")) {
                    update.add(Integer.parseInt(page.trim()));
                }
                updates.add(update);
            }
        }

        scanner.close();

        List<List<Integer>> correctedUpdates = new ArrayList<>();
        int sumOfMiddlePages = 0;

        // Check each update. Correct if necessary and calculate the sum of these corrected middle pages
        for (List<Integer> update : updates) {
            if (!isValidUpdate(update, rules)) {
                List<Integer> correctedUpdate = reorderUpdate(update, rules);
                correctedUpdates.add(correctedUpdate);

                int middlePage = correctedUpdate.get(correctedUpdate.size() / 2);
                sumOfMiddlePages += middlePage;
            }
        }

        System.out.println("\nCorrected Updates:");
        for (List<Integer> correctedUpdate : correctedUpdates) {
            System.out.println(correctedUpdate);
        }

        System.out.println("\nSum of Middle Pages: " + sumOfMiddlePages);
    }

    // Verify if an update satisfies the rules - Same as P1
    private static boolean isValidUpdate(List<Integer> update, Map<Integer, List<Integer>> rules) {
        Map<Integer, Integer> indexMap = new HashMap<>();

        for (int i = 0; i < update.size(); i++) {
            indexMap.put(update.get(i), i);
        }

        for (Map.Entry<Integer, List<Integer>> entry : rules.entrySet()) {
            int before = entry.getKey();
            for (int after : entry.getValue()) {
                if (indexMap.containsKey(before) && indexMap.containsKey(after)) {
                    if (indexMap.get(before) >= indexMap.get(after)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    // Reorder an update according to the rules
    private static List<Integer> reorderUpdate(List<Integer> update, Map<Integer, List<Integer>> rules) {
        // Map of pages and their dependencies (how many pages has the key (AFTER) before it (value))
        Map<Integer, Integer> dependencyCount = new HashMap<>(); 

        for (int page : update) {
            dependencyCount.put(page, 0);
        }

        // Count the dependencies of each page. How many pages are before (value) a certain page (key)
        for (Map.Entry<Integer, List<Integer>> entry : rules.entrySet()) {
            int before = entry.getKey();
            for (int after : entry.getValue()) {
                if (dependencyCount.containsKey(before) && dependencyCount.containsKey(after)) {
                    dependencyCount.put(after, dependencyCount.get(after) + 1);
                }
            }
        }

        // Add the pages without dependencies to the queue first
        Queue<Integer> queue = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : dependencyCount.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        // Reorder the update
        List<Integer> orderedUpdate = new ArrayList<>();
        while (!queue.isEmpty()) {
            int current = queue.poll(); //take first element of queue
            orderedUpdate.add(current);  

            // Update the dependencies of the pages that depend on the current page (the one added) subtracting 1
            for (int dependent : rules.getOrDefault(current, new ArrayList<>())) { 
                if (dependencyCount.containsKey(dependent)) {
                    dependencyCount.put(dependent, dependencyCount.get(dependent) - 1);
                    if (dependencyCount.get(dependent) == 0) { // If the page has no more dependencies
                        queue.add(dependent); // Add it to the queue and loop again
                    }
                }
            }
        }

        return orderedUpdate;
    }
}