// Day 3 - Part 2
// https://adventofcode.com/2024/day/3#part2

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3Part2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder input = new StringBuilder();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) {
                break;
            }
            input.append(line).append("\n");
        }

        String mulPattern = "mul\\((\\d+),(\\d+)\\)"; // Pattern to match only "mul(X, Y)"
        String doPattern = "do\\(\\)";               // Pattern to match "do()"
        String dontPattern = "don't\\(\\)";          // Pattern to match "don't()"

        Pattern mulRegex = Pattern.compile(mulPattern);
        Pattern doRegex = Pattern.compile(doPattern);
        Pattern dontRegex = Pattern.compile(dontPattern);

        Matcher mulMatcher = mulRegex.matcher(input.toString());
        Matcher doMatcher = doRegex.matcher(input.toString());
        Matcher dontMatcher = dontRegex.matcher(input.toString());

        boolean mulEnabled = true; // mul instructions are enabled at the beginning
        int sum = 0;
        int x;
        int y;

        int nextMulIndex;
        int nextDoIndex;
        int nextDontIndex;
        int currentIndex = 0;

        while (true) {
            // Find the next index from currentIndex of each pattern. If found, get the index, otherwise set it to Integer.MAX_VALUE
            // Edge case: --> If the string is very large (more than Integer.MAX_VALUE chars), process the string in chunks and ensure
            // that patterns at the chunk boundaries are not missed by adding an overlap between chunks.
            nextMulIndex = mulMatcher.find(currentIndex) ? mulMatcher.start() : Integer.MAX_VALUE;
            nextDoIndex = doMatcher.find(currentIndex) ? doMatcher.start() : Integer.MAX_VALUE;
            nextDontIndex = dontMatcher.find(currentIndex) ? dontMatcher.start() : Integer.MAX_VALUE;

            if (nextMulIndex == Integer.MAX_VALUE) {
                // If no more 'mul' patterns are found, break the loop
                break;
            }

            // Find the pattern that is found first and process it
            if (nextDoIndex < nextMulIndex && nextDoIndex < nextDontIndex) {
                // If the 'do' pattern is found first, enable multiplication
                mulEnabled = true;
                currentIndex = doMatcher.end();
            } else if (nextDontIndex < nextMulIndex) {
                // If the 'dont' pattern is found first, disable multiplication
                mulEnabled = false;
                currentIndex = dontMatcher.end();
            } else {
                if (mulEnabled) { 
                    x = Integer.parseInt(mulMatcher.group(1)); // Get the first number
                    y = Integer.parseInt(mulMatcher.group(2)); // Get the second number
                    sum += x * y;
                }
                currentIndex = mulMatcher.end(); // Move the current index to the end of the 'mul' pattern
            }
        }

        System.out.println("Result: " + sum);
    }
}