// Day 14 - Part 2
// https://adventofcode.com/2024/day/14#part2


import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class Day14Part2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Constants for the grid dimensions
        final int WIDTH = 101;
        final int HEIGHT = 103;
        final int START_SECOND = 6000; // Start checking from this second
        final int END_SECOND = 8000; // End checking at this second

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

        int[][] grid;
        // Simulate robots' movement for the specified range of seconds
        for (int seconds = START_SECOND; seconds <= END_SECOND; seconds++) {
            // Reset the grid for this second
            grid = new int[WIDTH][HEIGHT];
            for (Robot robot : robots) {
                grid[robot.x][robot.y]++; // Mark current positions
            }

            // Save current grid as an image, including initial state
            saveGridAsImage(grid, WIDTH, HEIGHT, seconds);

            // Move all the robots for the next second
            for (Robot robot : robots) {
                robot.move(1, WIDTH, HEIGHT);
            }
        }
    }

    // Method that saves the current grid as a PNG image. 
    // The file with lower size is the one we are looking for (as most of the robot will be in the same place)
    private static void saveGridAsImage(int[][] grid, int width, int height, int second) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int color;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                color = grid[x][y] > 0 ? 0x000000 : 0xFFFFFF;
                image.setRGB(x, y, color);
            }
        }

        try {
            File outputfile = new File("tmp/" + second + ".png");
            outputfile.getParentFile().mkdirs(); // Ensure directory exists
            ImageIO.write(image, "png", outputfile);
        } catch (Exception e) {
            System.err.println("Error saving image: " + e.getMessage());
        }
    }

    static class Robot {
        int x, y, vx, vy;

        public Robot(int x, int y, int vx, int vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }

        public void move(int seconds, int width, int height) {
            x = (x + vx * seconds) % width;
            y = (y + vy * seconds) % height;

            if (x < 0) x += width; // Handle negative wrapping for x
            if (y < 0) y += height; // Handle negative wrapping for y
        }
    }
}
