// Day 10 - Part 2
// https://adventofcode.com/2024/day/10#part2

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day10Part2 {
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
        System.out.println("Total rating of all trailheads: " + calculateTrailheadRatings(map, rows, cols));
    }

    // Method to calculate the total rating of all trailheads
    private static int calculateTrailheadRatings(int[][] map, int rows, int cols) {
        int totalRating = 0;

        // Allowed directions: up, down, left, right
        int[] dRow = {-1, 1, 0, 0};
        int[] dCol = {0, 0, -1, 1};

        // Look for all the positions with a 0 (trailhead), if found, call the recursive function
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == 0) {
                    // Call function to count the ratings of the paths from the trailheads
                    totalRating += countPathsFromTrailhead(map, i, j, -1, dRow, dCol, rows, cols);
                }
            }
        }

        return totalRating;
    }

    // Recursive method to count the ratings of the paths from a trailhead.
    // Uses a depth-first search approach.
    private static int countPathsFromTrailhead(int[][] map, int row, int col, int prevHeight,  int[] dRow, int[] dCol, int rows, int cols) {
        int currentHeight = map[row][col];
        
        // If we reach the end of the path, return 1
        if (currentHeight == 9) {
            return 1;
        }

        // Marcamos la celda como visitada temporalmente para evitar bucles
        map[row][col] = -1;

        int totalPaths = 0;
        int newRow, newCol;

        for (int i = 0; i < 4; i++) {
            newRow = row + dRow[i];
            newCol = col + dCol[i];
            
            // If the next cell is a valid one (in the limit and with one more height), call the function recursively
            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && map[newRow][newCol] == currentHeight + 1) {
                totalPaths += countPathsFromTrailhead(map, newRow, newCol, currentHeight, dRow, dCol, rows, cols);
            }
        }

        // Restaurar el valor original para otras exploraciones
        map[row][col] = currentHeight;

        return totalPaths;
    }
}
