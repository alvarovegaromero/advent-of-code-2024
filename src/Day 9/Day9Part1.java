// Day 9 - Part 1
// https://adventofcode.com/2024/day/9

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day9Part1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String inputLine = scanner.nextLine();

        scanner.close();

        List<Block> blocks = convertLineToBlocks(inputLine);

        //System.out.println("Blocks " + blocks);

        List<Block> compactedDisk = compactDisk(blocks);

        //System.out.println("Compacted disk: " + compactedDisk);

        System.out.print("Checksum: " + calculateChecksum(compactedDisk));

    }

    // Method to convert the input line to list of blocks
    private static List<Block> convertLineToBlocks(String inputLine) {
        List<Block> blocks = new ArrayList<>();
        int currentIndex = 0;
    
        for (int i = 0; i < inputLine.length(); i++) {
            int length = inputLine.charAt(i) - '0'; // Convert char to int
    
            // Add blocks based on the length of the current number
            for (int j = 0; j < length; j++) {
                StringBuilder blockContent = new StringBuilder();
    
                if (i % 2 == 0) {
                    blockContent.append(currentIndex); // Add the current index for even positions
                } else {
                    blockContent.append('.'); // Add dots for odd positions
                }
    
                blocks.add(new Block(blockContent.toString()));
            }
    
            if (i % 2 == 0) {
                currentIndex++; // Increment index only in even positions
            }
        }
    
        return blocks;
    }

    // Method to compact the disk state from individual blocks representation
    // by moving all the dots to the right and the right-placed blocks to the left
    private static List<Block> compactDisk(List<Block> individualBlocks) {
        int left = 0;
        int right = individualBlocks.size() - 1;

        while (left < right) {
            while (left < right && !individualBlocks.get(left).content.equals(".")) {
                left++;
            }
            while (left < right && individualBlocks.get(right).content.equals(".")) {
                right--;
            }
            if (left < right) {
                Block temp = individualBlocks.get(left);
                individualBlocks.set(left, individualBlocks.get(right));
                individualBlocks.set(right, temp);
                left++;
                right--;
            }
        }

        return individualBlocks;
    }

    // Method to calculate the checksum of the final disk state
    // Sum of block value * index
    private static long calculateChecksum(List<Block> compactedDisk) {
        long sum = 0;
    
        for (int i = 0; i < compactedDisk.size(); i++) {
            Block current = compactedDisk.get(i);
            if (current.content.equals(".")) {
                break;
            }
            int blockValue = Integer.parseInt(current.content);
            sum += (long) blockValue * i;
        }
    
        return sum;
    }
}

// Block class to represent each block in the disk
class Block {
    String content;

    public Block(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}