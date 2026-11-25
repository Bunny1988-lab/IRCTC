package com.irctc.divi;

import com.irctc.aditya.InMemoryDataStore;
import com.irctc.aditya.InputValidator;

public class AuthService {

    private final InMemoryDataStore dataStore;
    private User currentUser;

    public AuthService(InMemoryDataStore dataStore) {
        this.dataStore = dataStore;
        this.currentUser = null;
    }

    public User login(String username, String password) throws InputValidator.ValidationException {
        if (username == null || username.trim().isEmpty()) {
            throw new InputValidator.ValidationException("Username cannot be empty.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new InputValidator.ValidationException("Password cannot be empty.");
        }

        User user = dataStore.getUser(username.trim());
        if (user == null) {
            throw new InputValidator.ValidationException("User '" + username + "' not found. Please register first.");
        }

        if (!user.getPassword().equals(password)) {
            throw new InputValidator.ValidationException("Invalid password. Please try again.");
        }

        this.currentUser = user;
        return user;
    }

    public Passenger registerPassenger(String username, String password, String fullName,
                                       String email, String phone, int age, String gender)
            throws InputValidator.ValidationException {

        InputValidator.validateUsername(username);
        InputValidator.validatePassword(password);
        InputValidator.validateFullName(fullName);
        InputValidator.validateEmail(email);
        InputValidator.validatePhone(phone);
        InputValidator.validateAge(age);

        if (dataStore.isUsernameTaken(username)) {
            throw new InputValidator.ValidationException("Username '" + username + "' is already registered. Please choose another.");
        }

        String userId = "USR" + (dataStore.getUserCount() + 101);
        Passenger newPassenger = new Passenger(userId, username.trim(), password, fullName.trim(),
                email.trim(), phone.trim(), age, gender);

        dataStore.addUser(newPassenger);
        return newPassenger;
    }

    public void logout() {
        this.currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public boolean isAdmin() {
        return currentUser instanceof Admin;
    }
}
