// Day 8 - Part 1
// https://adventofcode.com/2024/day/8

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day8Part1 {
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
    
        // Calculate the antinodes each group of antennas
        for (Map.Entry<Character, List<Cell>> entry : antennaMap.entrySet()) {
            List<Cell> antennas = entry.getValue();
            for (int i = 0; i < antennas.size(); i++) {
                for (int j = i + 1; j < antennas.size(); j++) {
                    Cell a1 = antennas.get(i);
                    Cell a2 = antennas.get(j);
    
                    // Calculate the difference between the positions of the antennas
                    int dr = a2.row - a1.row;
                    int dc = a2.col - a1.col;

                    // Calculate the positions of the antinodes
                    int antinode1Row = a1.row - dr;
                    int antinode1Col = a1.col - dc;
                    int antinode2Row = a2.row + dr;
                    int antinode2Col = a2.col + dc;

                    // Mark the antinodes in the grid if they are within bounds
                    if (isWithinBounds(antinode1Row, antinode1Col, rows, cols)) {
                        markAntinode(grid, antinode1Row, antinode1Col);
                    }
                    if (isWithinBounds(antinode2Row, antinode2Col, rows, cols)) {
                        markAntinode(grid, antinode2Row, antinode2Col);
                    }
                }
            }
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