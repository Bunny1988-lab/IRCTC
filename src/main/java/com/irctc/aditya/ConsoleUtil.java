package com.irctc.aditya;

import java.util.Scanner;

public class ConsoleUtil {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void printHeader(String title) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("       " + title.toUpperCase());
        System.out.println("========================================");
    }

    public static void printSubHeader(String title) {
        System.out.println();
        System.out.println("--- " + title + " ---");
    }

    public static void printDivider() {
        System.out.println("----------------------------------------");
    }

    public static void printSuccess(String message) {
        System.out.println("[SUCCESS] " + message);
    }

    public static void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    public static void printInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    public static String readString(String prompt) {
        System.out.print(prompt + ": ");
        String line = SCANNER.nextLine();
        return line != null ? line.trim() : "";
    }

    public static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = SCANNER.nextLine();
            try {
                int value = Integer.parseInt(input.trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("[ERROR] Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Invalid number format. Please enter an integer.");
            }
        }
    }

    public static double readDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = SCANNER.nextLine();
            try {
                double value = Double.parseDouble(input.trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("[ERROR] Please enter a value between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Invalid number format. Please enter a decimal value.");
            }
        }
    }

    public static void pressEnterToContinue() {
        System.out.print("\nPress [Enter] to continue...");
        SCANNER.nextLine();
    }
}
