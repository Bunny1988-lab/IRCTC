package com.irctc.jyot;

import com.irctc.harbin.Coach;

import java.util.ArrayList;
import java.util.List;

public class Train implements Comparable<Train> {

    private String trainNumber;
    private String trainName;
    private String trainType;
    private String sourceStation;
    private String destinationStation;
    private Route route;
    private List<Coach> coaches;

    public Train() {
        this.coaches = new ArrayList<>();
    }

    public Train(String trainNumber, String trainName, String trainType,
                 String sourceStation, String destinationStation, Route route) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.trainType = trainType;
        this.sourceStation = sourceStation.toUpperCase();
        this.destinationStation = destinationStation.toUpperCase();
        this.route = route;
        this.coaches = new ArrayList<>();
    }

    public void addCoach(Coach coach) {
        if (coach != null) {
            coaches.add(coach);
        }
    }

    public Coach getCoach(String coachCode) {
        for (Coach c : coaches) {
            if (c.getCoachCode().equalsIgnoreCase(coachCode)) {
                return c;
            }
        }
        return null;
    }

    public int getTotalSeats() {
        int total = 0;
        for (Coach c : coaches) {
            total += c.getTotalSeatCount();
        }
        return total;
    }

    public int getAvailableSeats(String travelClass) {
        int count = 0;
        for (Coach c : coaches) {
            if (travelClass == null || c.getTravelClass().equalsIgnoreCase(travelClass)) {
                count += c.getAvailableSeatCount();
            }
        }
        return count;
    }

    @Override
    public int compareTo(Train other) {
        if (other == null || other.trainNumber == null) {
            return 1;
        }
        return this.trainNumber.compareTo(other.trainNumber);
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public void setSourceStation(String sourceStation) {
        this.sourceStation = sourceStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<Coach> getCoaches() {
        return coaches;
    }

    public void setCoaches(List<Coach> coaches) {
        this.coaches = coaches;
    }

    @Override
    public String toString() {
        return String.format("%-6s | %-24s | %-12s | %s -> %s | Total Seats: %d",
                trainNumber, trainName, trainType, sourceStation, destinationStation, getTotalSeats());
    }
}
