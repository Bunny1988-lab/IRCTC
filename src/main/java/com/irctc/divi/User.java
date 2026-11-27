package com.irctc.divi;

public abstract class User {

    protected String userId;
    protected String username;
    protected String password;
    protected String fullName;
    protected String email;
    protected String phone;
    protected String role;

    public User(String userId, String username, String password, String fullName, String email, String phone, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public abstract void displayProfile();

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
