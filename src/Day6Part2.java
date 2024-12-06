// Day 6 - Part 2
// https://adventofcode.com/2024/day/6#part2

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day6Part2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder inputBuilder = new StringBuilder();

        while (scanner.hasNextLine()) {
            inputBuilder.append(scanner.nextLine()).append("\n");
        }
        scanner.close();

        String[] map = inputBuilder.toString().split("\n");


        // Directions: 0 = upward, 1 = right, 2 = downward, 3 = left
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

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


        // SET to store the valid positions to place the obstruction
        Set<String> validPositions = new HashSet<>();

        // Iterate over the map to find the valid positions
        // Consider using a more efficient pathfinding algorithm like A* for better performance
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length(); j++) {
                // Skip the initial position and the already placed obstructions
                if ((i == startX && j == startY) || map[i].charAt(j) == '#') {
                    continue;
                }

                // Verify if placing an obstruction at this position causes a loop
                if (causesLoop(map, directions, startX, startY, currentDirection, i, j)) {
                    validPositions.add(i + "," + j);
                }
            }
        }

        System.out.println("Number of different positions that cause a loop in the guard: " + validPositions.size());
    }

    private static boolean causesLoop(String[] map, int[][] directions, int startX, int startY, int startDirection, int obstructionX, int obstructionY) {
        Set<String> visitedStates = new HashSet<>();
        int x = startX, y = startY, direction = startDirection;

        // Copy the map and place the obstruction
        char[][] modifiedMap = new char[map.length][];
        for (int i = 0; i < map.length; i++) {
            modifiedMap[i] = map[i].toCharArray();
        }
        modifiedMap[obstructionX][obstructionY] = '#';

        // Simulate movement of the guard
        while (true) {
            String currentState = x + "," + y + "," + direction;
            if (visitedStates.contains(currentState)) {
                // If the guard repeats a state, there is a loop
                return true;
            }
            visitedStates.add(currentState);

            int nextX = x + directions[direction][0];
            int nextY = y + directions[direction][1];

            // If the guard leaves the map, it is not a loop
            if (nextX < 0 || nextX >= map.length || nextY < 0 || nextY >= map[0].length()) {
                return false;
            }

            // If the guard finds an obstruction, change direction
            if (modifiedMap[nextX][nextY] == '#') {
                direction = (direction + 1) % 4;
            } else {
                // Move forward in current direction
                x = nextX;
                y = nextY;
            }

            // If it returns to the initial position and does not repeat the sequence, it is not a loop
            if (x == startX && y == startY && visitedStates.size() == 1) {
                return false;
            }
        }
    }
}

