// Day 15 - Part 1
// https://adventofcode.com/2024/day/15

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day15Part1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read the initial map and moves
        List<String> mapLines = new ArrayList<>();
        StringBuilder movesBuilder = new StringBuilder();
        
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) 
                break;
            mapLines.add(line);
        }
        
        while (scanner.hasNextLine()) {
            movesBuilder.append(scanner.nextLine().trim());
        }

        scanner.close();
                
        // Convert the map to a character matrix
        int rows = mapLines.size();
        int cols = mapLines.get(0).length();
        char[][] warehouse = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            warehouse[i] = mapLines.get(i).toCharArray();
        }

        // Convert the moves to a string
        String moves = movesBuilder.toString();

        // Find the initial position of the robot
        int[] robotPosition = findRobotPosition(warehouse);

        // Define movement directions. 
        // First element is row offset, and second element is column offset
        Map<Character, int[]> directions = new HashMap<>();
        directions.put('^', new int[] {-1, 0});
        directions.put('v', new int[] {1, 0});
        directions.put('<', new int[] {0, -1});
        directions.put('>', new int[] {0, 1});

        processMoves(warehouse, robotPosition, moves, directions);

        int gpsSum = calculateGpsSum(warehouse);

        System.out.println("Sum of GPS coordinates: " + gpsSum);
    }

    // Finds the initial position of the robot and replaces it with an empty space
    private static int[] findRobotPosition(char[][] warehouse) {
        for (int i = 0; i < warehouse.length; i++) {
            for (int j = 0; j < warehouse[i].length; j++) {
                if (warehouse[i][j] == '@') {
                    warehouse[i][j] = '.'; // Free the robot's position
                    return new int[] {i, j};
                }
            }
        }
        throw new IllegalStateException("Robot not found in the warehouse!");
    }

    // Processes the robot's moves and updates the warehouse state
    private static void processMoves(char[][] warehouse, int[] robotPosition, String moves, Map<Character, int[]> directions) {
        int[] dir, box;
        int newRow, newCol, currentRow, currentCol;
        
        for (char move : moves.toCharArray()) {
            // Move the robot in the specified direction
            dir = directions.get(move);
            newRow = robotPosition[0] + dir[0];
            newCol = robotPosition[1] + dir[1];

            // Check if the new position is valid (within the warehouse bounds and not a wall)
            if (isInsideWarehouse(newRow, newCol, warehouse) && warehouse[newRow][newCol] != '#') {
                // There's a box, check for a chain of boxes, and if there's free space 
                // after the last box, move the robot and the boxes
                if (warehouse[newRow][newCol] == 'O') { 
                    currentRow = newRow;
                    currentCol = newCol;
                    List<int[]> boxes = new ArrayList<>();

                    // Collect all consecutive boxes in the direction
                    while (isInsideWarehouse(currentRow, currentCol, warehouse) && warehouse[currentRow][currentCol] == 'O') {
                        boxes.add(new int[] {currentRow, currentCol});
                        currentRow += dir[0];
                        currentCol += dir[1];
                    }

                    // Check if there's free space after the last box
                    if (isInsideWarehouse(currentRow, currentCol, warehouse) && warehouse[currentRow][currentCol] == '.') {
                        // Move all boxes one step in the direction
                        for (int i = boxes.size() - 1; i >= 0; i--) {
                            box = boxes.get(i);
                            warehouse[box[0] + dir[0]][box[1] + dir[1]] = 'O'; // Move box to the new position
                            warehouse[box[0]][box[1]] = '.';                  // Clear the old position
                        }

                        // Move the robot to the position of the first box
                        robotPosition[0] = newRow;
                        robotPosition[1] = newCol;
                    }
                } else { // Move the robot if there's no box
                    robotPosition[0] = newRow;
                    robotPosition[1] = newCol;
                }
            }
        }

        // Place the robot's final position back on the map
        warehouse[robotPosition[0]][robotPosition[1]] = '@';
    }

    // Checks if a position is within the warehouse bounds
    private static boolean isInsideWarehouse(int row, int col, char[][] warehouse) {
        return row >= 0 && row < warehouse.length && col >= 0 && col < warehouse[0].length;
    }

    // Calculates the sum of GPS coordinates for all boxes
    private static int calculateGpsSum(char[][] warehouse) {
        int gpsSum = 0;
        for (int i = 0; i < warehouse.length; i++) {
            for (int j = 0; j < warehouse[i].length; j++) {
                if (warehouse[i][j] == 'O') {
                    gpsSum += (i * 100 + j);
                }
            }
        }
        return gpsSum;
    }
}
