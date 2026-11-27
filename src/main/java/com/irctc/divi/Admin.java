package com.irctc.divi;

public class Admin extends User {

    private String employeeId;
    private String department;

    public Admin(String userId, String username, String password, String fullName,
                 String email, String phone, String employeeId, String department) {
        super(userId, username, password, fullName, email, phone, "ADMIN");
        this.employeeId = employeeId;
        this.department = department;
    }

    @Override
    public void displayProfile() {
        System.out.println("============= ADMIN PROFILE =============");
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Username   : " + username);
        System.out.println("Full Name  : " + fullName);
        System.out.println("Department : " + department);
        System.out.println("Email      : " + email);
        System.out.println("Phone      : " + phone);
        System.out.println("Privilege  : FULL ACCESS (Add Trains, Stations, Schedules)");
        System.out.println("=========================================");
    }

}
