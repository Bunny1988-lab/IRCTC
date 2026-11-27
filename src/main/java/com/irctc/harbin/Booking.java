
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
    private String sourceCity;
    private String destinationCity;
    private List<Seat> allocatedSeats;
    private double totalFare;
    private String status;

    public Booking(String pnr, String bookedByUsername, Train train, String travelClass,
                   String travelDate, String sourceCity, String destinationCity,
                   double totalFare, String status) {
        this.pnr = pnr;
        this.bookedByUsername = bookedByUsername;
        this.train = train;
        this.travelClass = travelClass;
        this.travelDate = travelDate;
        this.sourceCity = sourceCity.trim();
        this.destinationCity = destinationCity.trim();
        this.totalFare = totalFare;
        this.status = status;
        this.allocatedSeats = new ArrayList<>();
    }

    public void addAllocatedSeat(Seat seat) {
        allocatedSeats.add(seat);
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

    public String getSourceCity() {
        return sourceCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public List<Seat> getAllocatedSeats() {
        return allocatedSeats;
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

    @Override
    public String toString() {
        return String.format(
                "PNR: %-10s | Train: %-5s %-16s | %s -> %s | Date: %-10s | Status: %-9s | Fare: Rs. %.2f",
                pnr, train.getTrainNumber(), train.getTrainName(),
                sourceCity, destinationCity, travelDate, status, totalFare
        );
    }
}
