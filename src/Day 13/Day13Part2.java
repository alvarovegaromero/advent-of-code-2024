import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day13Part2 {
    static class Machine {
        int ax, ay, bx, by;
        long px, py;

        public Machine(int ax, int ay, int bx, int by, long px, long py) {
            this.ax = ax;
            this.ay = ay;
            this.bx = bx;
            this.by = by;
            this.px = px;
            this.py = py;
        }
    }

    public static void main(String[] args) {
        final long PRIZE_OFFSET = 10000000000000L; // new constraint in part 2

        Scanner scanner = new Scanner(System.in);

        List<Machine> machines = new ArrayList<>();

        String lineA, lineB, lineP;
        int ax, ay, bx, by;
        long px, py;
        
        while (scanner.hasNextLine()) {
            lineA = scanner.nextLine().trim();
            if (lineA.isEmpty()) continue;

            lineB = scanner.nextLine().trim();
            lineP = scanner.nextLine().trim();
            
            ax = Integer.parseInt(lineA.split("X\\+")[1].split(",")[0].trim());
            ay = Integer.parseInt(lineA.split("Y\\+")[1].trim());
            bx = Integer.parseInt(lineB.split("X\\+")[1].split(",")[0].trim());
            by = Integer.parseInt(lineB.split("Y\\+")[1].trim());
            px = Long.parseLong(lineP.split("X=")[1].split(",")[0].trim()) + PRIZE_OFFSET;
            py = Long.parseLong(lineP.split("Y=")[1].trim()) + PRIZE_OFFSET;

            machines.add(new Machine(ax, ay, bx, by, px, py));

            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Skip empty line between entries
            }
        }

        scanner.close();

        int totalPrizes = 0;
        long totalCost = 0;
        long cost = 0;

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
    // Based Cramer's Rule (System of Linear Equations)
    private static long calculateMinCost(Machine machine) {
        // Calculate the determinant of the system
        long det = (long) machine.ax * machine.by - (long) machine.ay * machine.bx;
        if (det == 0) {
            return 0; // No solution if determinant is 0
        }

        // Calculate a and b using Cramer's rule
        long a = (machine.px * machine.by - machine.py * machine.bx) / det;
        long b = (machine.ax * machine.py - machine.ay * machine.px) / det;

        // Check if the solution is valid and a, b are non-negative
        if (a >= 0 && b >= 0 && machine.ax * a + machine.bx * b == machine.px && machine.ay * a + machine.by * b == machine.py) {
            return a * 3 + b; // Cost of the solution
        } else {
            return 0; // Invalid solution
        }
    }
}
