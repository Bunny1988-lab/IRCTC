package com.irctc.aditya;

import java.util.regex.Pattern;

public class InputValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[6-9]\\d{9}$");
    private static final Pattern PNR_PATTERN = Pattern.compile("^\\d{10}$");

    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }

    public static void validateUsername(String username) throws ValidationException {
        if (username == null || username.trim().length() < 3) {
            throw new ValidationException("Username must be at least 3 characters long.");
        }
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            throw new ValidationException("Username can only contain alphanumeric characters and underscores.");
        }
    }

    public static void validatePassword(String password) throws ValidationException {
        if (password == null || password.length() < 4) {
            throw new ValidationException("Password must be at least 4 characters long.");
        }
    }

    public static void validateFullName(String fullName) throws ValidationException {
        if (fullName == null || fullName.trim().length() < 2) {
            throw new ValidationException("Full name must be at least 2 characters.");
        }
    }

    public static void validateEmail(String email) throws ValidationException {
        if (email == null || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new ValidationException("Invalid email format. Example: user@example.com");
        }
    }

    public static void validatePhone(String phone) throws ValidationException {
        if (phone == null || !PHONE_PATTERN.matcher(phone.trim()).matches()) {
            throw new ValidationException("Invalid phone number. Must be a 10-digit Indian mobile number starting with 6-9.");
        }
    }

    public static void validateAge(int age) throws ValidationException {
        if (age < 1 || age > 120) {
            throw new ValidationException("Age must be between 1 and 120.");
        }
    }

    public static void validatePnr(String pnr) throws ValidationException {
        if (pnr == null || !PNR_PATTERN.matcher(pnr.trim()).matches()) {
            throw new ValidationException("PNR must be an exact 10-digit numeric code.");
        }
    }

    public static void validateStationCode(String code) throws ValidationException {
        if (code == null || code.trim().length() < 2 || code.trim().length() > 5) {
            throw new ValidationException("Station code must be 2 to 5 characters (e.g., NDLS, CNB, BSB).");
        }
    }

    public static void validateTrainNumber(String trainNumber) throws ValidationException {
        if (trainNumber == null || !trainNumber.trim().matches("^\\d{5}$")) {
            throw new ValidationException("Train number must be a 5-digit numeric string (e.g., 12301, 22436).");
        }
    }

    public static void validateChoice(int choice, int min, int max) throws ValidationException {
        if (choice < min || choice > max) {
            throw new ValidationException("Invalid choice: " + choice + ". Please select between " + min + " and " + max + ".");
        }
    }
}
