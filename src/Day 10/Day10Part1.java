// Day 10 - Part 1
// https://adventofcode.com/2024/day/10

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Day10Part1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> mapLines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            mapLines.add(scanner.nextLine());
        }
        scanner.close();

        int rows = mapLines.size();
        int cols = mapLines.get(0).length();
        int[][] map = new int[rows][cols];

        // Convert the map to a matrix of integers
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                map[i][j] = mapLines.get(i).charAt(j) - '0';
            }
        }

        // Calculate the total score of all trailheads
        System.out.println("Total score of all trailheads: " + calculateTrailheadScores(map, rows, cols));
    }

    // Method to calculate the total score of all trailheads
    private static int calculateTrailheadScores(int[][] map, int rows, int cols) {
        int totalScore = 0;

        // Allowed directions: up, down, left, right
        int[] dRow = {-1, 1, 0, 0};
        int[] dCol = {0, 0, -1, 1};

        // Look for all the positions with a 0 (trailhead), if found, call the recursive function
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == 0) {
                    // Call function to count the number of paths from the trailheads
                    totalScore += dfs(map, i, j, new HashSet<>(), dRow, dCol, rows, cols);
                }
            }
        }

        return totalScore;
    }

    // Recursive method to count the unique number of paths from a trailhead.
    // Uses a depth-first search approach.
    private static int dfs(int[][] map, int row, int col, Set<String> reachedNines,  int[] dRow, int[] dCol, int rows, int cols) {
        int currentHeight = map[row][col];
        
        // If we reach a 9, add it to the set of reached nines
        if (currentHeight == 9) {
            reachedNines.add(row + "," + col);
            return 0;
        }

        // Mark the cell as visited temporarily to avoid loops
        map[row][col] = -1;

        int newRow, newCol;

        for (int i = 0; i < 4; i++) {
            newRow = row + dRow[i];
            newCol = col + dCol[i];

            // If the next cell is a valid one (in the limit and with one more height), call the function recursively
            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && map[newRow][newCol] == currentHeight + 1) {
                dfs(map, newRow, newCol, reachedNines, dRow, dCol, rows, cols);
            }
        }

        // Restore the original value for other explorations
        map[row][col] = currentHeight;

        return reachedNines.size();
    }
}