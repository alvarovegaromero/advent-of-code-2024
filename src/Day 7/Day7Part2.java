// Day 7 - Part 2
// https://adventofcode.com/2024/day/7#part2

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Day7Part2 {
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

        long totalCalibrationResult = 0, testValue;
        String[] parts, numbers;
        long [] nums;

        // Process each line
        for (String line : inputLines) {
            parts = line.split(": ");
            if (parts.length != 2) {
                continue; // Skip malformed lines
            }

            try {
                testValue = Long.parseLong(parts[0].trim()); // Test value
                numbers = parts[1].trim().split(" "); // Numbers to use in the calculation
                nums = new long[numbers.length]; // Parsed numbers into long array
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
    
        long sumResult, mulResult, concatResult;
        for (int i = 1; i < numbers.length; i++) {
            Set<Long> newResults = new HashSet<>();
            for (long result : results) {
                sumResult = result + numbers[i];
                mulResult = result * numbers[i];
                concatResult = Long.parseLong(result + "" + numbers[i]);

                newResults.add(sumResult);
                newResults.add(mulResult);
                newResults.add(concatResult);
            }
            results = newResults;
        }
    
        return results.contains(testValue); // Check if the test value is in the SET
    }
}