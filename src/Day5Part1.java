// Day 5 - Part 1
// https://adventofcode.com/2024/day/5

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day5Part1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, List<Integer>> rules = new HashMap<>();
        List<List<Integer>> updates = new ArrayList<>();

        System.out.println("Enter rules and updates and then Ctrl+D or Ctrl+Z+Enter in Windows to finish");

        boolean readingRules = true;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) {
                readingRules = false; // When we find and empty line, change to read updates
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
                    update.add(Integer.parseInt(page.trim())); // Add updates to the list
                }
                updates.add(update);
            }
        }
        scanner.close();

        List<List<Integer>> validUpdates = new ArrayList<>();
        int sumOfMiddlePages = 0;

        // Check if each update is valid and calculate the sum of the middle pages
        for (List<Integer> update : updates) {
            if (isValidUpdate(update, rules)) {
                validUpdates.add(update);

                int middlePage = update.get(update.size() / 2);
                sumOfMiddlePages += middlePage;
            }
        }

        System.out.println("\nValid Updates:");
        for (List<Integer> validUpdate : validUpdates) {
            System.out.println(validUpdate);
        }

        System.out.println("\nSum of Middle Pages: " + sumOfMiddlePages);
    }

    // Método para verificar si una actualización cumple con las reglas
    private static boolean isValidUpdate(List<Integer> update, Map<Integer, List<Integer>> rules) {
        Map<Integer, Integer> indexMap = new HashMap<>(); // Map with the index of each number in the update

        for (int i = 0; i < update.size(); i++) {
            indexMap.put(update.get(i), i); 
        }

        // Verify each rule in the map
        for (Map.Entry<Integer, List<Integer>> entry : rules.entrySet()) {
            int before = entry.getKey(); // Page before
            for (int after : entry.getValue()) {
                if (indexMap.containsKey(before) && indexMap.containsKey(after)) { // If both pages are in the update
                    if (indexMap.get(before) >= indexMap.get(after)) { // The page "after" must be after the page "before"
                        return false; // A rule is broken --> not valid
                    }
                }
            }
        }

        return true; // All rules are satisfied --> valid
    }
}