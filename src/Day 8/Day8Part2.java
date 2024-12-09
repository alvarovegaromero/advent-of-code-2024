// Day 8 - Part 2
// https://adventofcode.com/2024/day/8#part2

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day8Part2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> inputLines = new ArrayList<>();
        String line;

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.trim().isEmpty()) {
                break;
            }
            inputLines.add(line);
        }

        scanner.close();

        int rows = inputLines.size();
        int cols = inputLines.get(0).length();
        Cell[][] grid = new Cell[rows][cols];

        // Create a matrix with the input
        for (int i = 0; i < rows; i++) {
            char[] rowChars = inputLines.get(i).toCharArray();
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Cell(rowChars[j], i, j);
            }
        }

        calculateAntinodes(grid, rows, cols);

        // Count and print the total unique antinode locations
        System.out.println("Total unique antinode locations: " + countUniqueAntinodes(grid));
    }

    public static void calculateAntinodes(Cell[][] grid, int rows, int cols) {
        // Map to store the antennas grouped by their character
        Map<Character, List<Cell>> antennaMap = new HashMap<>();
        
        char value;
        // Group the antennas by their character
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                value = grid[i][j].value;
                if (Character.isLetterOrDigit(value)) {
                    antennaMap.computeIfAbsent(value, k -> new ArrayList<>()).add(grid[i][j]);
                }
            }
        }
    
        // Calculate the antinodes for each group of antennas
        for (Map.Entry<Character, List<Cell>> entry : antennaMap.entrySet()) {
            List<Cell> antennas = entry.getValue();
            for (int i = 0; i < antennas.size(); i++) {
                for (int j = i + 1; j < antennas.size(); j++) {
                    Cell a1 = antennas.get(i);
                    Cell a2 = antennas.get(j);

                    // Calculate the difference between the positions of the antennas
                    int dr = a2.row - a1.row;
                    int dc = a2.col - a1.col;

                    // Mark the antinodes in the direction of the distance until the limits
                    markAntinodesInDirection(grid, a1.row, a1.col, -dr, -dc, rows, cols);
                    markAntinodesInDirection(grid, a2.row, a2.col, dr, dc, rows, cols);
                }
            }
        }
    }
        
    // Method to mark antinodes in a specific direction until the limits
    public static void markAntinodesInDirection(Cell[][] grid, int startRow, int startCol, int dr, int dc, int rows, int cols) {
        int currentRow = startRow;
        int currentCol = startCol;

        while (isWithinBounds(currentRow, currentCol, rows, cols)) {
            markAntinode(grid, currentRow, currentCol);
            currentRow += dr;
            currentCol += dc;
        }
    }

    // Method to check if a position is within the bounds of the grid
    public static boolean isWithinBounds(int row, int col, int rows, int cols) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    // Method to mark a cell as an antinode
    public static void markAntinode(Cell[][] grid, int row, int col) {
        if(grid[row][col].isAntinode == false)
            grid[row][col].isAntinode = true;
    }

    // Method to count unique antinodes
    public static int countUniqueAntinodes(Cell[][] grid) {
        int antinodesCount = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].isAntinode) {
                    antinodesCount++;
                }
            }
        }
        return antinodesCount;
    }
}

class Cell {
    int row;
    int col;
    char value;
    boolean isAntinode;

    Cell(char value, int row, int col) {
        this.row = row;
        this.col = col;
        this.value = value;
        this.isAntinode = false;
    }
}