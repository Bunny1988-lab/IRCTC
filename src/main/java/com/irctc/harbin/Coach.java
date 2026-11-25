package com.irctc.harbin;

import java.util.ArrayList;
import java.util.List;

public class Coach {

    private String coachCode;
    private String travelClass;
    private int rows;
    private int cols;

    private Seat[][] seatGrid;
    private List<Seat> seatList;

    public Coach(String coachCode, String travelClass, int rows, int cols) {
        this.coachCode = coachCode;
        this.travelClass = travelClass.toUpperCase();
        this.rows = rows;
        this.cols = cols;
        this.seatGrid = new Seat[rows][cols];
        this.seatList = new ArrayList<>();
        initializeSeatGrid();
    }

    private void initializeSeatGrid() {
        int seatCounter = 1;
        String[] berthTypes = {"LOWER", "MIDDLE", "UPPER", "SIDE_LOWER"};

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                String berth = berthTypes[c % berthTypes.length];
                Seat seat = new Seat(seatCounter, berth, r, c);
                seatGrid[r][c] = seat;
                seatList.add(seat);
                seatCounter++;
            }
        }
    }

    public int getTotalSeatCount() {
        return rows * cols;
    }

    public int getAvailableSeatCount() {
        int count = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!seatGrid[r][c].isBooked()) {
                    count++;
                }
            }
        }
        return count;
    }

    public Seat findNextAvailableSeat() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!seatGrid[r][c].isBooked()) {
                    return seatGrid[r][c];
                }
            }
        }
        return null;
    }

    public Seat getSeatByNumber(int seatNumber) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (seatGrid[r][c].getSeatNumber() == seatNumber) {
                    return seatGrid[r][c];
                }
            }
        }
        return null;
    }

    public void displaySeatMap() {
        System.out.println("\n--- Coach " + coachCode + " [" + travelClass + "] Seat Matrix ---");
        for (int r = 0; r < rows; r++) {
            System.out.printf("Row %2d | ", (r + 1));
            for (int c = 0; c < cols; c++) {
                Seat seat = seatGrid[r][c];
                char status = seat.isBooked() ? 'X' : 'O';
                System.out.printf("[%2d:%c] ", seat.getSeatNumber(), status);
            }
            System.out.println();
        }
        System.out.println("Legend: [O] = Available, [X] = Booked");
    }

    public String getCoachCode() {
        return coachCode;
    }

    public String getTravelClass() {
        return travelClass;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Seat[][] getSeatGrid() {
        return seatGrid;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }
}
