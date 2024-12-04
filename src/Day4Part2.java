// Day 4 - Part 2
// https://adventofcode.com/2024/day/4#part2

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day4Part2 {
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
        int count = wordSearch.findAllXMASPatterns();

        System.out.println("Total occurrences of X-MAS: " + count);
    }
}

class WordSearch {
    private final char[][] matrix;
    private final int rows;
    private final int cols;

    // Constructor that initializes the matrix from a list of strings
    public WordSearch(List<String> grid) {
        this.rows = grid.size();
        this.cols = grid.get(0).length();
        this.matrix = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            this.matrix[i] = grid.get(i).toCharArray();
        }
    }

    // Find all occurrences of X-MAS patterns in the matrix
    public int findAllXMASPatterns() {
        int count = 0;

        // Iterate through the matrix to find potential centers of the "X". Borders are
        // excluded
        for (int x = 1; x < rows - 1; x++) { // Start from the second row untill the second last row
            for (int y = 1; y < cols - 1; y++) { // Same but with the second column
                if (checkXMAS(x, y)) {
                    count++;
                }
            }
        }

        return count;
    }

    // Check if there's an X-MAS pattern centered at (x, y)
    private boolean checkXMAS(int x, int y) {
        
        // Check if the center is 'A' and verify the 'X' structure
        if (matrix[x][y] == 'A') {
            // If I had more time, I would optimize this by defining the 4 possible "X-MAS" patterns in a list of relative 
            // positions for the 'M's and 'S's around the center 'A', then iterate through these patterns 
            // to check if the positions match the expected characters ('M' or 'S'). 
            // This approach would eliminate redundant conditionals and make the code more scalable and maintainable.

            // M.S
            // .A.
            // M.S
            if (matrix[x - 1][y - 1] == 'M' && matrix[x + 1][y + 1] == 'S' &&
                matrix[x - 1][y + 1] == 'M' && matrix[x + 1][y - 1] == 'S') 
                    return true;
            
            // M.M
            // .A.
            // S.S
            if (matrix[x - 1][y - 1] == 'M' && matrix[x - 1][y + 1] == 'S' &&
                matrix[x + 1][y - 1] == 'M' && matrix[x + 1][y + 1] == 'S') 
                    return true;

            // S.M
            // .A.
            // S.M
            if (matrix[x + 1][y - 1] == 'M' && matrix[x - 1][y - 1] == 'S' &&
                matrix[x + 1][y + 1] == 'M' && matrix[x - 1][y + 1] == 'S')
                    return true;
            
            // S.S
            // .A.
            // M.M
            if (matrix[x - 1][y + 1] == 'M' && matrix[x - 1][y - 1] == 'S' &&
                matrix[x + 1][y + 1] == 'M' && matrix[x + 1][y - 1] == 'S') 
                    return true;
        }

        return false;
    }
}