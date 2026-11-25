package com.irctc.jyot;

public class TrainSchedule {

    private String scheduleId;
    private Train train;
    private String travelDate;
    private String departureTime;
    private String arrivalTime;
    private int availableSeats;

    public TrainSchedule(String scheduleId, Train train, String travelDate,
                         String departureTime, String arrivalTime, int availableSeats) {
        this.scheduleId = scheduleId;
        this.train = train;
        this.travelDate = travelDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void decrementSeats(int count) {
        this.availableSeats = Math.max(0, this.availableSeats - count);
    }

    public void incrementSeats(int count) {
        this.availableSeats += count;
    }

    @Override
    public String toString() {
        return String.format("%-8s | Train: %-5s %-20s | Date: %-10s | %s -> %s | Seats: %d",
                scheduleId, train.getTrainNumber(), train.getTrainName(),
                travelDate, departureTime, arrivalTime, availableSeats);
    }
}
