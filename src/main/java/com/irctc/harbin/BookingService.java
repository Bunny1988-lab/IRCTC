
package com.irctc.harbin;

import com.irctc.aditya.ConsoleUtil;
import com.irctc.aditya.FareCalculator;
import com.irctc.aditya.InMemoryDataStore;
import com.irctc.aditya.InputValidator;
import com.irctc.aditya.PnrGenerator;
import com.irctc.divi.Passenger;
import com.irctc.divi.User;
import com.irctc.jyot.Route;
import com.irctc.jyot.Train;

import java.util.ArrayList;
import java.util.List;

public class BookingService {

    private final InMemoryDataStore dataStore;

    public static class BookingException extends Exception {
        public BookingException(String message) {
            super(message);
        }
    }

    public static class PassengerInput {
        private String name;
        private int age;
        private String gender;

        public PassengerInput(String name, int age, String gender) {
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        public String getName() { return name; }
        public int getAge() { return age; }
        public String getGender() { return gender; }
    }

    public BookingService(InMemoryDataStore dataStore) {
        this.dataStore = dataStore;
    }

    public Ticket bookTicket(String username, String trainNumber, String travelClass,
                             String travelDate, String fromCity, String toCity,
                             List<PassengerInput> passengers) throws BookingException {

        try {
            InputValidator.validateTravelDate(travelDate);
        } catch (InputValidator.ValidationException e) {
            throw new BookingException(e.getMessage());
        }

        if (passengers == null || passengers.isEmpty()) {
            throw new BookingException("At least one passenger must be specified for booking.");
        }

        if (passengers.size() > 6) {
            throw new BookingException("Maximum 6 passengers allowed per booking transaction.");
        }

        Train train = dataStore.getTrain(trainNumber);

        if (train == null) {
            throw new BookingException("Train number " + trainNumber + " not found.");
        }

        Route route = train.getRoute();

        if (route == null || !route.isCityBefore(fromCity, toCity)) {
            throw new BookingException(
                    "Train " + trainNumber + " does not run from "
                            + fromCity + " to " + toCity + " in sequence."
            );
        }

        double distanceKm = route.getDistanceBetween(fromCity, toCity);

        double totalFare = 0.0;

        for (PassengerInput p : passengers) {
            totalFare += FareCalculator.calculateFare(travelClass, distanceKm, p.getAge());
        }

        Coach targetCoach = null;

        for (Coach c : train.getCoaches()) {
            if (c.getTravelClass().equalsIgnoreCase(travelClass)
                    && c.getAvailableSeatCount() >= passengers.size()) {
                targetCoach = c;
                break;
            }
        }

        if (targetCoach == null) {
            int totalAvailable = train.getAvailableSeats(travelClass);

            if (totalAvailable < passengers.size()) {
                for (PassengerInput p : passengers) {
                    dataStore.addToWaitingList(
                            trainNumber + ":" + p.getName() + " (" + travelClass + ")"
                    );
                }

                throw new BookingException(
                        "All seats in " + travelClass
                                + " are fully booked. Passengers added to waiting list queue."
                );
            }
        }

        List<Seat> allocated = new ArrayList<>();

        for (PassengerInput p : passengers) {
            Seat seat = targetCoach.findNextAvailableSeat();

            if (seat == null) {
                for (Seat rollbackSeat : allocated) {
                    rollbackSeat.release();
                }

                throw new BookingException(
                        "Seat allocation failed mid-process. Transaction rolled back."
                );
            }

            seat.book(p.getName());
            allocated.add(seat);
        }

        String pnr = PnrGenerator.generatePnr();

        while (dataStore.getBooking(pnr) != null) {
            pnr = PnrGenerator.generatePnr();
        }

        Booking booking = new Booking(
                pnr, username, train, travelClass, travelDate,
                fromCity, toCity, totalFare, "CONFIRMED"
        );

        for (int i = 0; i < allocated.size(); i++) {
            booking.addAllocatedSeat(allocated.get(i));
        }

        dataStore.addBooking(booking);

        User user = dataStore.getUser(username);

        if (user instanceof Passenger) {
            ((Passenger) user).addPnr(pnr);
        }

        String ticketId = "TKT-" + pnr.substring(0, 6);

        return new Ticket(ticketId, booking);
    }

    public boolean cancelTicket(String pnr) throws BookingException {
        if (pnr == null || pnr.trim().isEmpty()) {
            throw new BookingException("PNR cannot be empty.");
        }

        Booking booking = dataStore.getBooking(pnr.trim());

        if (booking == null) {
            throw new BookingException("No booking found with PNR: " + pnr);
        }

        if ("CANCELLED".equalsIgnoreCase(booking.getStatus())) {
            throw new BookingException("Booking with PNR " + pnr + " is already CANCELLED.");
        }

        for (Seat seat : booking.getAllocatedSeats()) {
            seat.release();
        }

        booking.setStatus("CANCELLED");
        String nextWaiting = dataStore.pollWaitingList();

        if (nextWaiting != null) {
            ConsoleUtil.printInfo(
                    "Seat freed up! Next waitlisted passenger notified: " + nextWaiting
            );
        }

        return true;
    }

    public Booking getPnrStatus(String pnr) {
        if (pnr == null) return null;
        return dataStore.getBooking(pnr.trim());
    }

    public List<Booking> getBookingsByUser(String username) {
        List<Booking> userBookings = new ArrayList<>();

        if (username == null) return userBookings;

        for (Booking b : dataStore.getAllBookings()) {
            if (username.equalsIgnoreCase(b.getBookedByUsername())) {
                userBookings.add(b);
            }
        }

        return userBookings;
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<>(dataStore.getAllBookings());
    }
}
