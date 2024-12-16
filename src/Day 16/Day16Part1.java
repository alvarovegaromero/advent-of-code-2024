// Day 16 - Part 1
// https://adventofcode.com/2024/day/16

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class Day16Part1 {

    // Define the four possible directions: East, South, West, North
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private static final char START = 'S';
    private static final char END = 'E';
    private static final char WALL = '#';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Read the map input
        List<String> mapInput = new ArrayList<>();
        String line;

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.isEmpty()) break;
            mapInput.add(line);
        }

        char[][] map = mapInput.stream().map(String::toCharArray).toArray(char[][]::new);
        int result = findLowestScore(map);
        System.out.println("Lowest score: " + result);
    }

    // Finds the lowest score to navigate from S to E
    // Uses an A* search algorithm to explore possible moves, prioritizing the lowest cost
    private static int findLowestScore(char[][] map) {
        // Locate start and end positions
        int[] start = findPosition(map, START);
        int[] end = findPosition(map, END);

        // Priority queue to explore possible moves based on cost
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));
        Set<String> visited = new HashSet<>();

        // Start facing East (direction = 0)
        queue.offer(new State(start[0], start[1], 0, 0));

        State current;
        String stateKey;
        int[] dir;
        int newX, newY, newCost, turnCost;

        // Explore possible moves until the queue is empty
        while (!queue.isEmpty()) {
            current = queue.poll();
            stateKey = current.x + "," + current.y + "," + current.direction;

            if (visited.contains(stateKey)) continue;
            visited.add(stateKey);

            // Check if we've reached the end
            if (current.x == end[0] && current.y == end[1]) {
                return current.cost;
            }

            // Explore possible moves, adding the cost of moving forward and turning
            for (int i = 0; i < DIRECTIONS.length; i++) {
                dir = DIRECTIONS[i];
                newX = current.x + dir[0];
                newY = current.y + dir[1];

                if (isValidMove(map, newX, newY)) {
                    newCost = current.cost + 1; // Moving forward adds 1 point
                    turnCost = (i == current.direction) ? 0 : 1000; // Turning adds 1000 points
                    queue.offer(new State(newX, newY, i, newCost + turnCost));
                }
            }
        }

        return -1; // No path found
    }

    // Finds the position of a specific character in the map
    private static int[] findPosition(char[][] map, char target) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("Target " + target + " not found in map.");
    }

    // Checks if a move is valid (inside bounds and not a wall)
    private static boolean isValidMove(char[][] map, int x, int y) {
        return x >= 0 && y >= 0 && x < map.length && y < map[0].length && map[x][y] != WALL;
    }

    // Represents the state of the Reindeer during navigation
    private static class State {
        int x, y, direction, cost;

        State(int x, int y, int direction, int cost) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.cost = cost;
        }
    }
}

