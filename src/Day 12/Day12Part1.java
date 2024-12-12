// Day 12 - Part 1
// https://adventofcode.com/2024/day/12

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Day12Part1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> plantMap = new ArrayList<>();
        String line;

        while (scanner.hasNextLine()) {
            line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                break;
            }
            plantMap.add(line);
        }

        scanner.close();

        // Convert the list of strings to a 2D character array
        char[][] map = plantMap.stream()
                               .map(String::toCharArray)
                               .toArray(char[][]::new);

        // Create a 2D boolean array to keep track of visited cells
        boolean[][] visited = new boolean[map.length][map[0].length];
        int totalCost = 0;
        Region region;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // If the cell has not been visited yet, explore the region (after, all the region will be mark as visited)
                if (!visited[i][j]) {
                    region = exploreRegion(map, visited, i, j, map[i][j]);
                    totalCost += (region.area * region.perimeter);
                }
            }
        }

        System.out.println("Total cost of fencing all regions: " + totalCost);
    }

    // Method to explore the current region of a plant and return the area and perimeter
    private static Region exploreRegion(char[][] map, boolean[][] visited, int x, int y, char plantType) {
        int area = 0, perimeter = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});
        visited[x][y] = true;

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        int[] current;
        int newX, newY;

        // Breadth-first search to explore the region
        while (!queue.isEmpty()) {
            current = queue.poll();
            area++;

            for (int[] direction : directions) {
                newX = current[0] + direction[0];
                newY = current[1] + direction[1];
                
                // If the new cell is out of bounds or has a different plant type, increment the perimeter
                if (newX < 0 || newX >= map.length || newY < 0 || newY >= map[0].length || map[newX][newY] != plantType) {
                    perimeter++;
                } 
                // If the new cell has not been visited yet, mark it as visited and add it to the queue to process it later
                else if (!visited[newX][newY]) {
                    visited[newX][newY] = true;
                    queue.add(new int[]{newX, newY});
                }
            }
        }

        return new Region(area, perimeter);
    }

    // Class to represent a region with its area (number of plants) and perimeter (fences to place at the border)
    private static class Region {
        int area;
        int perimeter;

        Region(int area, int perimeter) {
            this.area = area;
            this.perimeter = perimeter;
        }
    }
}
