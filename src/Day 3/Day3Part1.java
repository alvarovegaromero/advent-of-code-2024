// Day 3 - Part 1
// https://adventofcode.com/2024/day/3

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3Part1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder input = new StringBuilder();
        String pattern = "mul\\((\\d+),(\\d+)\\)"; // Pattern to match only "mul(X, Y)"

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) {
                break;
            }
            input.append(line).append("\n");
        }

        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input.toString());

        int sum = 0;
        int x;
        int y;
        while (matcher.find()) {
            x = Integer.parseInt(matcher.group(1)); // Get the first number
            y = Integer.parseInt(matcher.group(2)); // Get the second number
            sum += x * y;
        }

        System.out.println("Result: " + sum);
    }
}
