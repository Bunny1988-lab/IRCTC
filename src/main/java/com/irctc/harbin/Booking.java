package com.irctc.harbin;

import com.irctc.jyot.Train;

import java.util.ArrayList;
import java.util.List;

public class Booking {

    private String pnr;
    private String bookedByUsername;
    private Train train;
    private String travelClass;
    private String travelDate;
    private String sourceStation;
    private String destinationStation;
    private List<Seat> allocatedSeats;
    private List<String> passengerNames;
    private double totalFare;
    private String status;
    private String bookingTimestamp;

    public Booking(String pnr, String bookedByUsername, Train train, String travelClass,
                   String travelDate, String sourceStation, String destinationStation,
                   double totalFare, String status) {
        this.pnr = pnr;
        this.bookedByUsername = bookedByUsername;
        this.train = train;
        this.travelClass = travelClass;
        this.travelDate = travelDate;
        this.sourceStation = sourceStation.toUpperCase();
        this.destinationStation = destinationStation.toUpperCase();
        this.totalFare = totalFare;
        this.status = status;
        this.allocatedSeats = new ArrayList<>();
        this.passengerNames = new ArrayList<>();
        this.bookingTimestamp = java.time.LocalDate.now().toString();
    }

    public void addAllocatedSeat(Seat seat, String passengerName) {
        allocatedSeats.add(seat);
        passengerNames.add(passengerName);
    }

    public String getPnr() {
        return pnr;
    }

    public String getBookedByUsername() {
        return bookedByUsername;
    }

    public Train getTrain() {
        return train;
    }

    public String getTravelClass() {
        return travelClass;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public List<Seat> getAllocatedSeats() {
        return allocatedSeats;
    }

    public List<String> getPassengerNames() {
        return passengerNames;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookingTimestamp() {
        return bookingTimestamp;
    }

    @Override
    public String toString() {
        return String.format("PNR: %-10s | Train: %-5s %-16s | %s -> %s | Date: %-10s | Status: %-9s | Fare: Rs. %.2f",
                pnr, train.getTrainNumber(), train.getTrainName(),
                sourceStation, destinationStation, travelDate, status, totalFare);
    }
}
