package com.irctc.jyot;

import java.util.ArrayList;
import java.util.List;

public class Route {

    private String routeId;
    private String sourceStation;
    private String destinationStation;
    private List<String> stationCodes;
    private List<Double> cumulativeDistancesKm;
    private List<String> arrivalTimes;
    private List<String> departureTimes;

    public Route(String routeId, String sourceStation, String destinationStation) {
        this.routeId = routeId;
        this.sourceStation = sourceStation.toUpperCase();
        this.destinationStation = destinationStation.toUpperCase();
        this.stationCodes = new ArrayList<>();
        this.cumulativeDistancesKm = new ArrayList<>();
        this.arrivalTimes = new ArrayList<>();
        this.departureTimes = new ArrayList<>();
    }

    public void addStop(String stationCode, double distanceKm, String arrival, String departure) {
        stationCodes.add(stationCode.toUpperCase());
        cumulativeDistancesKm.add(distanceKm);
        arrivalTimes.add(arrival);
        departureTimes.add(departure);
    }

    public boolean containsStation(String stationCode) {
        if (stationCode == null) return false;
        return stationCodes.contains(stationCode.trim().toUpperCase());
    }

    public int getStopIndex(String stationCode) {
        if (stationCode == null) return -1;
        return stationCodes.indexOf(stationCode.trim().toUpperCase());
    }

    public boolean isStationBefore(String stationA, String stationB) {
        int idxA = getStopIndex(stationA);
        int idxB = getStopIndex(stationB);
        return idxA != -1 && idxB != -1 && idxA < idxB;
    }

    public double getDistanceBetween(String stationA, String stationB) {
        int idxA = getStopIndex(stationA);
        int idxB = getStopIndex(stationB);
        if (idxA != -1 && idxB != -1 && idxA < idxB) {
            return cumulativeDistancesKm.get(idxB) - cumulativeDistancesKm.get(idxA);
        }
        return 0.0;
    }

    public double getTotalDistance() {
        if (cumulativeDistancesKm.isEmpty()) return 0.0;
        return cumulativeDistancesKm.get(cumulativeDistancesKm.size() - 1);
    }

    public String getRouteId() {
        return routeId;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public List<String> getStationCodes() {
        return stationCodes;
    }

    public List<Double> getCumulativeDistancesKm() {
        return cumulativeDistancesKm;
    }

    public List<String> getArrivalTimes() {
        return arrivalTimes;
    }

    public List<String> getDepartureTimes() {
        return departureTimes;
    }
}
