// Day 11 - Part 1
// https://adventofcode.com/2024/day/11

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day11Part1 {
    final static int blinks = 25;
    
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String inputLine = scanner.nextLine();

        scanner.close();

        List<String> finalStones = stoneReposition(inputLine, blinks);
        //System.out.println(String.join(" ", finalStones));
        System.out.println("Number of final stones: " + finalStones.size());
    }

    // Method to reposition the stones a certain number of times
    public static List<String> stoneReposition(String inputLine, int blinks) {
        List<String> stones = new ArrayList<>();

        for (String stone : inputLine.split(" ")) {
            stones.add(stone);
        }

        for (int i = 0; i < blinks; i++) {
            stones = reposition(stones);
        }

        return stones;
    }

    // Method to reposition the stones based on the rules given in the problem
    // Brute Force approach 
    // Not the most efficient way to solve the problem but easy and fast to implement and 
    // works fine with low number of blinks
    public static List<String> reposition(List<String> stones) {
        List<String> newStones = new ArrayList<>();

        for (String stone : stones) { // Rules given in the problem
            // If the stone is 0, replace it with 1
            if (stone.equals("0")) {
                newStones.add("1");
            } 
            // If the stone has an even number of digit, split in to half
            // Don't keep extra 0s
            else if (stone.length() % 2 == 0) {
                int mid = stone.length() / 2;
                String left = stone.substring(0, mid);
                String right = stone.substring(mid);
                newStones.add(String.valueOf(Integer.parseInt(left)));
                newStones.add(String.valueOf(Integer.parseInt(right)));
            } 
            // Else, just multiply the stone value by 2024
            else {
                long newStoneValue = Long.parseLong(stone) * 2024;
                newStones.add(String.valueOf(newStoneValue));
            }
        }

        return newStones;
    }
}