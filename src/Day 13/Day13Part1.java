// Day 13 - Part 1
// https://adventofcode.com/2024/day/13

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day13Part1 {
    static class Machine {
        int ax, ay, bx, by, px, py;

        public Machine(int ax, int ay, int bx, int by, int px, int py) {
            this.ax = ax;
            this.ay = ay;
            this.bx = bx;
            this.by = by;
            this.px = px;
            this.py = py;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Machine> machines = new ArrayList<>();

        String lineA, lineB, lineP;
        int ax, ay, bx, by, px, py;
        
        while (scanner.hasNextLine()) {
            lineA = scanner.nextLine().trim();
            if (lineA.isEmpty()) continue;

            lineB = scanner.nextLine().trim();
            lineP = scanner.nextLine().trim();
            
            ax = Integer.parseInt(lineA.split("X\\+")[1].split(",")[0].trim());
            ay = Integer.parseInt(lineA.split("Y\\+")[1].trim());
            bx = Integer.parseInt(lineB.split("X\\+")[1].split(",")[0].trim());
            by = Integer.parseInt(lineB.split("Y\\+")[1].trim());
            px = Integer.parseInt(lineP.split("X=")[1].split(",")[0].trim());
            py = Integer.parseInt(lineP.split("Y=")[1].trim());

            machines.add(new Machine(ax, ay, bx, by, px, py));

            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Skip empty line between entries
            }
        }

        scanner.close();

        int totalPrizes = 0;
        int totalCost = 0;
        int cost = 0;

        for (Machine machine : machines) {
            cost = calculateMinCost(machine);
            if (cost > 0) { // Only count valid solutions
                totalPrizes++;
                totalCost += cost;
            }
        }

        System.out.println("Maximum prizes that can be won: " + totalPrizes);
        System.out.println("Minimum total cost: " + totalCost);
    }

    // Returns the minimum cost to reach the prize location
    // Uses a dynamic programming approach to calculate the minimum cost
    private static int calculateMinCost(Machine machine) {
        int maxPresses = 100; // Maximum number of presses allowed
        int[][] dp = new int[maxPresses + 1][maxPresses + 1];
        
        for (int[] row : dp) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        dp[0][0] = 0; // Initial state

        int currentX, currentY;

        // Iterate over possible presses of button A and button B
        for (int a = 0; a <= maxPresses; a++) {
            for (int b = 0; b <= maxPresses; b++) {
                if (dp[a][b] == Integer.MAX_VALUE) continue; // Skip unreachable states

                currentX = a * machine.ax + b * machine.bx;
                currentY = a * machine.ay + b * machine.by;

                // Check if the current position matches the prize location
                if (currentX == machine.px && currentY == machine.py) {
                    return dp[a][b];
                }

                // Update the cost for pressing button A one more time
                if (a + 1 <= maxPresses) {
                    dp[a + 1][b] = Math.min(dp[a + 1][b], dp[a][b] + 3);
                }

                // Update the cost for pressing button B one more time
                if (b + 1 <= maxPresses) {
                    dp[a][b + 1] = Math.min(dp[a][b + 1], dp[a][b] + 1);
                }
            }
        }

        // Return maximum value if no solution is found. 
        return 0;
    }
}
