
package com.irctc.aditya;

import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;

public class InputValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[6-9]\\d{9}$");
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

    public static void validateTrainNumber(String trainNumber) throws ValidationException {
        if (trainNumber == null || !trainNumber.trim().matches("^\\d{5}$")) {
            throw new ValidationException("Train number must be a 5-digit numeric string (e.g., 12301, 22436).");
        }
    }

    public static void validateTravelDate(String travelDate) throws ValidationException {
        if (travelDate == null || !travelDate.trim().matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            throw new ValidationException("Invalid date format. Please enter the date as YYYY-MM-DD.");
        }

        try {
            LocalDate date = LocalDate.parse(
                    travelDate.trim(),
                    DateTimeFormatter.ISO_LOCAL_DATE
            );

            if (date.isBefore(LocalDate.now())) {
                throw new ValidationException("Travel date cannot be in the past.");
            }

        } catch (DateTimeParseException e) {
            throw new ValidationException("Invalid calendar date. Please enter a valid date.");
        }
    }

}
