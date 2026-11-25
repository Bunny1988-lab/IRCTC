package com.irctc.aditya;

import java.util.Random;

public class PnrGenerator {

    private static final Random RANDOM = new Random();

    public static String generatePnr() {
        int firstDigit = 6 + RANDOM.nextInt(3);
        long remaining9Digits = 100000000L + (long)(RANDOM.nextDouble() * 900000000L);
        return String.valueOf(firstDigit) + String.valueOf(remaining9Digits);
    }
}
