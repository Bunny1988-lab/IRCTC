package com.irctc.jyot;

import com.irctc.aditya.InMemoryDataStore;
import com.irctc.aditya.InputValidator;
import com.irctc.harbin.Coach;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainService {

    private final InMemoryDataStore dataStore;

    public TrainService(InMemoryDataStore dataStore) {
        this.dataStore = dataStore;
    }

    public List<Train> searchTrains(String source, String destination) {
        List<Train> matchingTrains = new ArrayList<>();

        if (source == null || destination == null) {
            return matchingTrains;
        }

        String src = source.trim();
        String dest = destination.trim();

        if (src.isEmpty() || dest.isEmpty() || src.equalsIgnoreCase(dest)) {
            return matchingTrains;
        }

        for (Train train : dataStore.getAllTrains()) {
            Route route = train.getRoute();

            if (route != null && route.isCityBefore(src, dest)) {
                matchingTrains.add(train);
            }
        }

        Collections.sort(matchingTrains);
        return matchingTrains;
    }

    public List<Train> getAllTrains() {
        List<Train> trains = new ArrayList<>(dataStore.getAllTrains());
        Collections.sort(trains);
        return trains;
    }

    public List<Station> getAllStations() {
        List<Station> stations = new ArrayList<>(dataStore.getAllStations());
        Collections.sort(stations);
        return stations;
    }

    public Train getTrainByNumber(String trainNumber) {
        if (trainNumber == null) return null;
        return dataStore.getTrain(trainNumber.trim());
    }

    public void addStation(Station station) throws InputValidator.ValidationException {
        if (station == null) {
            throw new InputValidator.ValidationException("Station cannot be null.");
        }

        if (station.getCity() == null || station.getCity().trim().isEmpty()) {
            throw new InputValidator.ValidationException("City name cannot be empty.");
        }

        if (station.getStationName() == null || station.getStationName().trim().isEmpty()) {
            throw new InputValidator.ValidationException("Station name cannot be empty.");
        }

        if (dataStore.getStation(station.getCity()) != null) {
            throw new InputValidator.ValidationException(
                    "Station for city " + station.getCity() + " already exists."
            );
        }

        dataStore.addStation(station);
    }

    public void addTrain(Train train) throws InputValidator.ValidationException {
        if (train == null) {
            throw new InputValidator.ValidationException("Train cannot be null.");
        }

        InputValidator.validateTrainNumber(train.getTrainNumber());

        if (train.getTrainName() == null || train.getTrainName().trim().isEmpty()) {
            throw new InputValidator.ValidationException("Train name cannot be empty.");
        }

        if (dataStore.getTrain(train.getTrainNumber()) != null) {
            throw new InputValidator.ValidationException(
                    "Train with number " + train.getTrainNumber() + " already exists."
            );
        }

        dataStore.addTrain(train);
    }

    public void displayTrainDetails(String trainNumber) {
        Train train = getTrainByNumber(trainNumber);

        if (train == null) {
            System.out.println("[ERROR] Train not found.");
            return;
        }

        System.out.println("\n========================================");
        System.out.println(" TRAIN DETAILS: " + train.getTrainNumber() + " - " + train.getTrainName());
        System.out.println("========================================");
        System.out.println("Type        : " + train.getTrainType());
        System.out.println("Origin      : " + train.getSourceCity());
        System.out.println("Destination : " + train.getDestinationCity());
        System.out.println("Total Seats : " + train.getTotalSeats());

        Route route = train.getRoute();

        if (route != null) {
            System.out.println("\n--- Route & Halt Schedule ---");

            System.out.printf("%-4s | %-20s | %-12s | %-8s | %-8s%n",
                    "Stop", "City", "Distance(Km)", "Arrival", "Departure");

            System.out.println("----------------------------------------------------------------");

            List<String> cities = route.getCities();
            List<Double> dists = route.getCumulativeDistancesKm();
            List<String> arrs = route.getArrivalTimes();
            List<String> deps = route.getDepartureTimes();

            for (int i = 0; i < cities.size(); i++) {
                System.out.printf("%-4d | %-20s | %-12.1f | %-8s | %-8s%n",
                        (i + 1), cities.get(i), dists.get(i), arrs.get(i), deps.get(i));
            }
        }

        System.out.println("\n--- Coach Composition & Seat Availability ---");

        for (Coach coach : train.getCoaches()) {
            System.out.printf("Coach %-4s [%-3s] : %2d/%2d available%n",
                    coach.getCoachCode(), coach.getTravelClass(),
                    coach.getAvailableSeatCount(), coach.getTotalSeatCount());
        }

        System.out.println("========================================");
    }
}
