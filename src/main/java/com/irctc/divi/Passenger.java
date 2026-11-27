package com.irctc.divi;

import java.util.ArrayList;
import java.util.List;

public class Passenger extends User {

    // Removed: no-argument Passenger() constructor.
    // The application always creates passengers with complete registration details.
    private int age;
    private String gender;
    private List<String> bookedPnrs;

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

    // Removed: removePnr(), setAge(), setGender(),
    // getBookedPnrs(), and setBookedPnrs().
    // These methods had no callers or runtime effect.
    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

}
