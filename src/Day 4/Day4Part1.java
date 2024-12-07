// Day 4 - Part 1
// https://adventofcode.com/2024/day/4

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day4Part1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> grid = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) {
                break;
            }
            grid.add(line);
        }

        // Create an instance of WordSearch
        WordSearch wordSearch = new WordSearch(grid);
        String target = "XMAS";
        int count = wordSearch.findAllOccurrences(target);

        System.out.println("Total occurrences of " + target + ": " + count);
    }
}

class WordSearch {
    private final char[][] matrix;
    private final int rows;
    private final int cols;
    private final int[][] directions = {
        {0, 1}, {1, 1}, {1, 0}, {1, -1},
        {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}
    }; // right, down-right, down, down-left, left, up-left, up, up-right

    // Constructor that initializes the matrix from a list of strings
    public WordSearch(List<String> grid) {
        this.rows = grid.size();
        this.cols = grid.get(0).length();
        this.matrix = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            this.matrix[i] = grid.get(i).toCharArray();
        }
    }

    // Find all occurrences of a word in the matrix
    public int findAllOccurrences(String target) {
        int count = 0;

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                if (matrix[x][y] == target.charAt(0)) { // Cell may be the start of the word only if it matches the first character of the word
                    for (int[] dir : directions) {
                        if (checkWord(target, x, y, dir[0], dir[1])) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }

    // Verify if the word is present in a cell of the matrix in a particular direction
    private boolean checkWord(String target, int x, int y, int dx, int dy) {
        int len = target.length();

        for (int i = 0; i < len; i++) {
            int nx = x + i * dx; // Next X coordinate to check
            int ny = y + i * dy; // Next Y coordinate to check

            // If the cell is out of bounds or the character in the cell is not the same as the character in the target word, return false
            if (nx < 0 || nx >= rows || ny < 0 || ny >= cols || matrix[nx][ny] != target.charAt(i)) {
                return false;
            }
        }
        
        return true;
    }
}