package com.irctc.divi;

import java.util.ArrayList;
import java.util.List;

public class Passenger extends User {

    private int age;
    private String gender;
    private List<String> bookedPnrs;

    public Passenger() {
        super();
        this.role = "PASSENGER";
        this.bookedPnrs = new ArrayList<>();
    }

    public Passenger(String userId, String username, String password, String fullName,
                     String email, String phone, int age, String gender) {
        super(userId, username, password, fullName, email, phone, "PASSENGER");
        this.age = age;
        this.gender = gender;
        this.bookedPnrs = new ArrayList<>();
    }

    @Override
    public void displayProfile() {
        System.out.println("========== PASSENGER PROFILE ==========");
        System.out.println("User ID   : " + userId);
        System.out.println("Username  : " + username);
        System.out.println("Full Name : " + fullName);
        System.out.println("Email     : " + email);
        System.out.println("Phone     : " + phone);
        System.out.println("Age       : " + age);
        System.out.println("Gender    : " + gender);
        System.out.println("Total PNRs: " + bookedPnrs.size());
        System.out.println("========================================");
    }

    public void addPnr(String pnr) {
        if (pnr != null && !bookedPnrs.contains(pnr)) {
            bookedPnrs.add(pnr);
        }
    }

    public void removePnr(String pnr) {
        bookedPnrs.remove(pnr);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getBookedPnrs() {
        return bookedPnrs;
    }

    public void setBookedPnrs(List<String> bookedPnrs) {
        this.bookedPnrs = bookedPnrs;
    }
}
