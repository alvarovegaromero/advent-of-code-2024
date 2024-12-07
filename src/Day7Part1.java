// Day 7 - Part 1
// https://adventofcode.com/2024/day/7

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Day7Part1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> inputLines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                inputLines.add(line);
            }
        }
        scanner.close();

        long totalCalibrationResult = 0;

        // Process each line
        for (String line : inputLines) {
            String[] parts = line.split(": ");
            if (parts.length != 2) {
                continue; // Skip malformed lines
            }

            try {
                long testValue = Long.parseLong(parts[0].trim()); // Test value
                String[] numbers = parts[1].trim().split(" "); // Numbers to use in the calculation
                long[] nums = new long[numbers.length]; // Parsed numbers into long array
                for (int i = 0; i < numbers.length; i++) {
                    nums[i] = Long.parseLong(numbers[i]);
                }
                if (canMatchTestValue(testValue, nums)) { 
                    totalCalibrationResult += testValue;
                }
            } catch (NumberFormatException e) {
                // Skip lines with invalid numbers
                continue;
            }
        }

        System.out.println("Total Calibration Result: " + totalCalibrationResult);
    }

    // Function to check if any combination of operators can match the test value
    // Create a SET with all the possible results and check if the test value is in the SET
    private static boolean canMatchTestValue(long testValue, long[] numbers) {
        Set<Long> results = new HashSet<>();
        results.add(numbers[0]);
    
        for (int i = 1; i < numbers.length; i++) {
            Set<Long> newResults = new HashSet<>();
            for (long result : results) {
                long sumResult = result + numbers[i];
                long mulResult = result * numbers[i];
                if (sumResult == testValue || mulResult == testValue) {
                    return true; // Found a match - return early and avoid unnecessary calculations
                }
                newResults.add(sumResult);
                newResults.add(mulResult);
            }
            results = newResults;
        }
    
        return results.contains(testValue); // Check if the test value is in the SET
    }
}