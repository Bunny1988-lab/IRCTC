package com.irctc.harbin;

public class Seat {

    private int seatNumber;
    private String berthType;
    private int row;
    private int col;
    private boolean isBooked;
    private String passengerName;
    private int passengerAge;
    private String passengerGender;

    public Seat(int seatNumber, String berthType, int row, int col) {
        this.seatNumber = seatNumber;
        this.berthType = berthType;
        this.row = row;
        this.col = col;
        this.isBooked = false;
        this.passengerName = "";
        this.passengerAge = 0;
        this.passengerGender = "";
    }

    public void book(String name, int age, String gender) {
        this.isBooked = true;
        this.passengerName = name != null ? name.trim() : "";
        this.passengerAge = age;
        this.passengerGender = gender != null ? gender.trim() : "";
    }

    public void release() {
        this.isBooked = false;
        this.passengerName = "";
        this.passengerAge = 0;
        this.passengerGender = "";
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getBerthType() {
        return berthType;
    }

    public void setBerthType(String berthType) {
        this.berthType = berthType;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public int getPassengerAge() {
        return passengerAge;
    }

    public String getPassengerGender() {
        return passengerGender;
    }

    @Override
    public String toString() {
        return String.format("Seat %2d [%-10s] - %s",
                seatNumber, berthType, (isBooked ? "BOOKED (" + passengerName + ")" : "AVAILABLE"));
    }
}
