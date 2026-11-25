package com.irctc.aditya;

public class FareCalculator {

    public static final double RATE_SLEEPER = 0.60;
    public static final double RATE_3A = 1.40;
    public static final double RATE_2A = 2.10;
    public static final double RATE_1A = 3.20;
    public static final double MIN_FARE = 50.0;

    public static double calculateFare(String travelClass, double distanceKm, int age) {
        double ratePerKm = getRatePerKm(travelClass);
        double distance = Math.max(distanceKm, 50.0);
        double baseFare = distance * ratePerKm;

        double concessionMultiplier = 1.0;
        if (age >= 60) {
            concessionMultiplier = 0.60;
        } else if (age < 12) {
            concessionMultiplier = 0.50;
        }

        double finalFare = baseFare * concessionMultiplier;

        if (finalFare < MIN_FARE) {
            finalFare = MIN_FARE;
        }

        return Math.round(finalFare);
    }

    public static double getRatePerKm(String travelClass) {
        if (travelClass == null) {
            return RATE_SLEEPER;
        }
        String cleanClass = travelClass.trim().toUpperCase();
        if (cleanClass.equals("3A") || cleanClass.equals("3_AC") || cleanClass.equals("AC3")) {
            return RATE_3A;
        } else if (cleanClass.equals("2A") || cleanClass.equals("2_AC") || cleanClass.equals("AC2")) {
            return RATE_2A;
        } else if (cleanClass.equals("1A") || cleanClass.equals("1_AC") || cleanClass.equals("AC1")) {
            return RATE_1A;
        } else {
            return RATE_SLEEPER;
        }
    }
}
