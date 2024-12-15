// Day 14 - Part 1
// https://adventofcode.com/2024/day/14

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day14Part1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Constants for the grid dimensions and simulation time
        final int WIDTH = 101;
        final int HEIGHT = 103;
        final int SECONDS = 100;

        List<Robot> robots = new ArrayList<>();
        String line;
        String[] parts, position, velocity;
        int px, py, vx, vy;

        // Reading input
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.trim().isEmpty()) break;
            parts = line.split(" ");
            position = parts[0].substring(2).split(",");
            velocity = parts[1].substring(2).split(",");

            px = Integer.parseInt(position[0]);
            py = Integer.parseInt(position[1]);
            vx = Integer.parseInt(velocity[0]);
            vy = Integer.parseInt(velocity[1]);

            robots.add(new Robot(px, py, vx, vy));
        }

        // Simulate robots' movement for 100 seconds in the grid
        int[][] grid = new int[WIDTH][HEIGHT];
        for (Robot robot : robots) {
            robot.move(SECONDS, WIDTH, HEIGHT);
            grid[robot.x][robot.y]++;
        }

        // Calculate robots in each quadrant
        int midX = WIDTH / 2;
        int midY = HEIGHT / 2;
        int q1 = 0, q2 = 0, q3 = 0, q4 = 0;

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (grid[x][y] > 0) {
                    if (x == midX || y == midY) continue; // Skip robots on the middle lines
                    if (x < midX && y < midY) q1 += grid[x][y]; // Top-left quadrant
                    else if (x >= midX && y < midY) q2 += grid[x][y]; // Top-right quadrant
                    else if (x < midX && y >= midY) q3 += grid[x][y]; // Bottom-left quadrant
                    else if (x >= midX && y >= midY) q4 += grid[x][y]; // Bottom-right quadrant
                }
            }
        }

        // Compute safety factor
        int safetyFactor = q1 * q2 * q3 * q4;
        System.out.println("Safety Factor: " + safetyFactor);
    }

    // Class to represent a robot
    static class Robot {
        int x, y, vx, vy;

        public Robot(int x, int y, int vx, int vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }

        // Moves the robot for a given time within the grid boundaries
        public void move(int seconds, int width, int height) {
            x = (x + vx * seconds) % width;
            y = (y + vy * seconds) % height;

            if (x < 0) x += width; // Handle negative wrapping for x
            if (y < 0) y += height; // Handle negative wrapping for y
        }
    }
}

