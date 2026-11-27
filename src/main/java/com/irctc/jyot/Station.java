
package com.irctc.jyot;

public class Station implements Comparable<Station> {

    private String stationName;
    private String city;
    private int platformCount;

    public Station(String stationName, String city, int platformCount) {
        this.stationName = stationName;
        this.city = city;
        this.platformCount = platformCount;
    }

    @Override
    public int compareTo(Station other) {
        if (other == null || other.city == null) {
            return 1;
        }
        return this.city.compareToIgnoreCase(other.city);
    }

    public String getStationName() {
        return stationName;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return String.format("%-25s | %-18s | Platforms: %d",
                stationName, city, platformCount);
    }
}
