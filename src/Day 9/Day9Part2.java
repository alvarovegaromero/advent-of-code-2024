// Day 9 - Part 1
// https://adventofcode.com/2024/day/9

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day9Part2 {
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

    // Method to compact the disk
    // Move the blocks to the left as much as possible (to the first free space found)
    // Done quickly but could be optimized by saving free spaces' indices and sizes
    // Also, return the position of the last block moved so the calculation of the checksum can be done faster
    // Use linked list to make the insert and removal of blocks faster (just change the pointers)...
    private static List<Block> compactDisk(List<Block> individualBlocks) {
        int n = individualBlocks.size();
    
        // Iterate from the back to the front of the disk
        for (int i = n - 1; i >= 0; i--) {
            Block currentBlock = individualBlocks.get(i);
            if (currentBlock.content.equals(".")) continue;
    
            // Count how many blocks are with the same content as the current block
            int fileSize = 0;
            while (i - fileSize >= 0 && individualBlocks.get(i - fileSize).content.equals(currentBlock.content)) {
                fileSize++;
            }
    
            // Look for a free space to move the blocks as far left as possible
            int freeSpaceStart = -1;
            int freeSpaceSize = 0;
            for (int j = 0; j < i; j++) {
                if (individualBlocks.get(j).content.equals(".")) {
                    if (freeSpaceStart == -1) freeSpaceStart = j;
                    freeSpaceSize++;
                    if (freeSpaceSize == fileSize) break;
                } else {
                    freeSpaceStart = -1;
                    freeSpaceSize = 0;
                }
            }
    
            // Move the blocks to the free space
            if (freeSpaceSize >= fileSize) {
                for (int j = 0; j < fileSize; j++) {
                    individualBlocks.set(freeSpaceStart + j, new Block(currentBlock.content));
                    individualBlocks.set(i - j, new Block("."));
                }
            }
    
            // Adjust the index to skip the blocks that were moved
            i -= (fileSize - 1);
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
                continue;
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