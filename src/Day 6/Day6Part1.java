// Day 6 - Part 1
// https://adventofcode.com/2024/day/6

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day6Part1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder inputBuilder = new StringBuilder();
        
        // Read the input until there are no more lines or CTRL+D / CTRL+Z+ENTER is pressed
        while (scanner.hasNextLine()) {
            inputBuilder.append(scanner.nextLine()).append("\n");
        }
        
        // Split the input into lines
        String[] map = inputBuilder.toString().split("\n");
        scanner.close();



        // Directions: 0 = upward, 1 = right, 2 = downward, 3 = left
        int[][] directions = { {-1, 0}, {0, 1}, {1, 0}, {0, -1} };

        // Check for the direction the guard is facing at first and find the initial position
        int currentDirection = -1; // -1 indicates no direction found
        int startX = -1, startY = -1; // -1 indicates no position found
        char currentChar;

        outerLoop:
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length(); j++) {
                currentChar = map[i].charAt(j);
                switch (currentChar) {
                    case '<' -> {
                        currentDirection = 2; // Left
                        startX = i;
                        startY = j;
                        break outerLoop;
                    }
                    case '^' -> {
                        currentDirection = 0; // Up
                        startX = i;
                        startY = j;
                        break outerLoop;
                    }
                    case '>' -> {
                        currentDirection = 1; // Right
                        startX = i;
                        startY = j;
                        break outerLoop;
                    }
                    case 'v' -> {
                        currentDirection = 3; // Down
                        startX = i;
                        startY = j;
                        break outerLoop;
                    }
                }
            }
        }

        // SET to store the visited positions
        Set<String> visitedPositions = new HashSet<>();
        visitedPositions.add(startX + "," + startY);

        int x = startX, y = startY;

        while (true) {
            int nextX = x + directions[currentDirection][0];
            int nextY = y + directions[currentDirection][1];

            // If the guard leaves the map, stop and count
            if (nextX < 0 || nextX >= map.length || nextY < 0 || nextY >= map[0].length()) {
                break;
            }

            // If there is an obstable, turn 90 degrees to the right
            if (map[nextX].charAt(nextY) == '#') {
                currentDirection = (currentDirection + 1) % 4;
            } else {
                // Move forward in current direction
                x = nextX;
                y = nextY;
                visitedPositions.add(x + "," + y);
            }
        }

        System.out.println("Total number of distinct positions visited: " + visitedPositions.size());
    }
}