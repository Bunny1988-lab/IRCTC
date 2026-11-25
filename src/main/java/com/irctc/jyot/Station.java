package com.irctc.jyot;

public class Station implements Comparable<Station> {

    private String stationCode;
    private String stationName;
    private String city;
    private int platformCount;

    public Station() {
    }

    public Station(String stationCode, String stationName, String city, int platformCount) {
        this.stationCode = stationCode != null ? stationCode.trim().toUpperCase() : "";
        this.stationName = stationName;
        this.city = city;
        this.platformCount = platformCount;
    }

    @Override
    public int compareTo(Station other) {
        if (other == null || other.stationCode == null) {
            return 1;
        }
        return this.stationCode.compareTo(other.stationCode);
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode != null ? stationCode.trim().toUpperCase() : "";
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPlatformCount() {
        return platformCount;
    }

    public void setPlatformCount(int platformCount) {
        this.platformCount = platformCount;
    }

    @Override
    public String toString() {
        return String.format("%-6s | %-25s | %-18s | Platforms: %d",
                stationCode, stationName, city, platformCount);
    }
}
