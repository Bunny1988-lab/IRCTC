package com.irctc.harbin;

public class Seat {

    private int seatNumber;
    private String berthType;
    private boolean isBooked;
    private String passengerName;

    public Seat(int seatNumber, String berthType) {
        this.seatNumber = seatNumber;
        this.berthType = berthType;
        this.isBooked = false;
        this.passengerName = "";
    }

    public void book(String name) {
        this.isBooked = true;
        this.passengerName = name != null ? name.trim() : "";
    }

    public void release() {
        this.isBooked = false;
        this.passengerName = "";
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public String getBerthType() {
        return berthType;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public String getPassengerName() {
        return passengerName;
    }

    @Override
    public String toString() {
        return String.format("Seat %2d [%-10s] - %s",
                seatNumber, berthType, (isBooked ? "BOOKED (" + passengerName + ")" : "AVAILABLE"));
    }
}
