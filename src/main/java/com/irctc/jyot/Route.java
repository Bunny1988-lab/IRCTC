
package com.irctc.jyot;

import java.util.ArrayList;
import java.util.List;

public class Route {

    private List<String> cities;
    private List<Double> cumulativeDistancesKm;
    private List<String> arrivalTimes;
    private List<String> departureTimes;

    public Route() {
        this.cities = new ArrayList<>();
        this.cumulativeDistancesKm = new ArrayList<>();
        this.arrivalTimes = new ArrayList<>();
        this.departureTimes = new ArrayList<>();
    }

    public void addStop(String city, double distanceKm, String arrival, String departure) {
        cities.add(city.trim());
        cumulativeDistancesKm.add(distanceKm);
        arrivalTimes.add(arrival);
        departureTimes.add(departure);
    }

    public int getStopIndex(String city) {
        if (city == null) return -1;

        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).equalsIgnoreCase(city.trim())) {
                return i;
            }
        }

        return -1;
    }

    public boolean isCityBefore(String cityA, String cityB) {
        int idxA = getStopIndex(cityA);
        int idxB = getStopIndex(cityB);
        return idxA != -1 && idxB != -1 && idxA < idxB;
    }

    public double getDistanceBetween(String cityA, String cityB) {
        int idxA = getStopIndex(cityA);
        int idxB = getStopIndex(cityB);

        if (idxA != -1 && idxB != -1 && idxA < idxB) {
            return cumulativeDistancesKm.get(idxB) - cumulativeDistancesKm.get(idxA);
        }

        return 0.0;
    }

    public List<String> getCities() {
        return cities;
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
