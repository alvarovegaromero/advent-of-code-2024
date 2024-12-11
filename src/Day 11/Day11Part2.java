import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day11Part2 {
    final static int blinks = 75;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputLine = scanner.nextLine();
        scanner.close();

        // Save the number of the stones in a map how many times has been repeated
        Map<String, Long> finalStones = stoneReposition(inputLine, blinks);

        // Calculate the total number of stones by summing the values of the map
        long totalStones = finalStones.values().stream().mapToLong(Long::longValue).sum();
        System.out.println("Number of final stones: " + totalStones);
    }

    // Method to reposition the stones a certain number of times
    public static Map<String, Long> stoneReposition(String inputLine, int blinks) {
        Map<String, Long> stones = new HashMap<>();

        for (String stone : inputLine.split(" ")) {
            stones.put(stone, stones.getOrDefault(stone, 0L) + 1);
        }

        for (int i = 0; i < blinks; i++) {
            stones = reposition(stones);
        }

        return stones;
    }

    // Method to reposition the stones based on the rules given in the problem
    // Dynamic Counting Approach
    // More efficient way to solve the problem with a high number of blinks as less memory and time is used
    public static Map<String, Long> reposition(Map<String, Long> stones) {
        Map<String, Long> newStones = new HashMap<>();

        for (Map.Entry<String, Long> entry : stones.entrySet()) {
            String stone = entry.getKey();
            long count = entry.getValue();

            // If the stone is 0, replace it with 1
            if (stone.equals("0")) {
                newStones.put("1", newStones.getOrDefault("1", 0L) + count);
            } 
            // If the stone has an even number of digit, split in to half
            // Don't keep extra 0s
            else if (stone.length() % 2 == 0) {
                int mid = stone.length() / 2;
                String left = String.valueOf(Integer.parseInt(stone.substring(0, mid)));
                String right = String.valueOf(Integer.parseInt(stone.substring(mid)));
                newStones.put(left, newStones.getOrDefault(left, 0L) + count);
                newStones.put(right, newStones.getOrDefault(right, 0L) + count);
            }
            // Else, just multiply the stone value by 2024 
            else {
                long newStoneValue = Long.parseLong(stone) * 2024;
                String newStone = String.valueOf(newStoneValue);
                newStones.put(newStone, newStones.getOrDefault(newStone, 0L) + count);
            }
        }

        return newStones;
    }
}