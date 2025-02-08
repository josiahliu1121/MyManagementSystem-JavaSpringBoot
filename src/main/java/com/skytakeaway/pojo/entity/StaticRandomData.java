package com.skytakeaway.pojo.entity;

import com.skytakeaway.pojo.vo.DataVO;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Data
public class StaticRandomData {
    // Static variable holding the online player count
    public static int onlinePlayerCount = (int) (Math.random() * 1000000);

    // Method to change the value of onlinePlayerCount randomly
    public synchronized static void changeValue() {
        int random = (int) (Math.random() * 10);

        if (random % 2 > 0) {
            onlinePlayerCount += random;
        } else {
            onlinePlayerCount -= random;
        }

        // Ensure the value is always non-negative
        if (onlinePlayerCount < 0) {
            onlinePlayerCount = 0;
        }
    }

    public static DataVO generateRandomData(){
        return DataVO.builder()
                .sexRatio((int) (Math.random() * 100))
                .ageRatio(generateRandomNumbers(5,100))
                .predictedPlayerCount(generateRandomNumberList(7,1000))
                .reviewCount(generateRandomNumberList(5,2000))
                .saleCount1(generateRandomNumberList(12,500))
                .saleCount2(generateRandomNumberList(12,500))
                .downloadCount(generateRandomNumberList(6,1000))
                .build();
    }

    public static List<Integer> generateRandomNumbers(int count, int total) {
        Random random = new Random();
        List<Integer> breakpoints = new ArrayList<>();

        // Generate 4 random breakpoints between 1 and 99
        for (int i = 0; i < count - 1; i++) {
            breakpoints.add(random.nextInt(total - 1) + 1);
        }

        // Add 0 and total as boundary points
        breakpoints.add(0);
        breakpoints.add(total);

        // Sort breakpoints
        Collections.sort(breakpoints);

        // Calculate differences between breakpoints
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i < breakpoints.size(); i++) {
            result.add(breakpoints.get(i) - breakpoints.get(i - 1));
        }

        return result;
    }

    public static List<Integer> generateRandomNumberList(int count, int max) {
        List<Integer> result = new ArrayList<>();
        List<Integer> boundaries = new ArrayList<>();

        // Generate (count - 1) random boundary points
        for (int i = 0; i < count - 1; i++) {
            boundaries.add((int) (Math.random() * max));
        }

        // Add 0 and max as boundaries
        boundaries.add(0);
        boundaries.add(max);
        Collections.sort(boundaries);

        // Compute the differences to get random values summing to max
        for (int i = 1; i < boundaries.size(); i++) {
            result.add(boundaries.get(i) - boundaries.get(i - 1));
        }

        return result;
    }
}
