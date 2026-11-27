
package com.irctc.harbin;

import java.util.List;

public class Ticket {

    private String ticketId;
    private Booking booking;

    public Ticket(String ticketId, Booking booking) {
        this.ticketId = ticketId;
        this.booking = booking;
    }

    public String generateTicketSummary() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n==================================================================\n");
        sb.append("                 INDIAN RAILWAYS E-TICKET RECEIPT                 \n");
        sb.append("==================================================================\n");
        sb.append(String.format(" Ticket ID : %-20s PNR : %-10s%n", ticketId, booking.getPnr()));
        sb.append(String.format(" Status    : %-20s Date: %-10s%n", booking.getStatus(), booking.getTravelDate()));
        sb.append("------------------------------------------------------------------\n");
        sb.append(String.format(" Train     : %s - %s%n", booking.getTrain().getTrainNumber(), booking.getTrain().getTrainName()));
        sb.append(String.format(" Route     : %s -> %s%n", booking.getSourceCity(), booking.getDestinationCity()));
        sb.append(String.format(" Class     : %-20s Booked By: %s%n", booking.getTravelClass(), booking.getBookedByUsername()));
        sb.append("------------------------------------------------------------------\n");
        sb.append(" PASSENGER & SEAT ALLOCATION:\n");
        sb.append(String.format(" %-4s | %-22s | %-8s | %-12s%n", "#", "Passenger Name", "Seat No", "Berth Type"));
        sb.append(" ----------------------------------------------------------------\n");

        List<Seat> seats = booking.getAllocatedSeats();

        for (int i = 0; i < seats.size(); i++) {
            Seat s = seats.get(i);

            sb.append(String.format(" %-4d | %-22s | %-8d | %-12s%n",
                    (i + 1), s.getPassengerName(), s.getSeatNumber(), s.getBerthType()));
        }

        sb.append("------------------------------------------------------------------\n");
        sb.append(String.format(" TOTAL FARE PAID : Rs. %.2f (All taxes included)%n", booking.getTotalFare()));
        sb.append("==================================================================\n");
        sb.append("   Thank you for choosing IRCTC. Please carry a valid Photo ID!   \n");
        sb.append("==================================================================\n");

        return sb.toString();
    }

    public void printTicket() {
        System.out.println(generateTicketSummary());
    }

}
