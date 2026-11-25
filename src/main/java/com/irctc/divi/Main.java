package com.irctc.divi;

import com.irctc.aditya.ConsoleUtil;
import com.irctc.aditya.InMemoryDataStore;
import com.irctc.aditya.InputValidator;
import com.irctc.harbin.Booking;
import com.irctc.harbin.BookingService;
import com.irctc.harbin.Coach;
import com.irctc.harbin.Ticket;
import com.irctc.jyot.Route;
import com.irctc.jyot.Station;
import com.irctc.jyot.Train;
import com.irctc.jyot.TrainService;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String APP_NAME = "IRCTC RAILWAY RESERVATION";
    private static final String APP_VERSION = "2.0.0-IN-MEMORY";

    private final InMemoryDataStore dataStore;
    private final AuthService authService;
    private final TrainService trainService;
    private final BookingService bookingService;

    public Main() {
        this.dataStore = InMemoryDataStore.getInstance();
        this.authService = new AuthService(dataStore);
        this.trainService = new TrainService(dataStore);
        this.bookingService = new BookingService(dataStore);
    }

    public static void main(String[] args) {
        Main app = new Main();

        if (args != null && args.length > 0 && "--non-interactive".equalsIgnoreCase(args[0])) {
            System.out.println("========================================");
            System.out.println("       " + APP_NAME);
            System.out.println("                 Version " + APP_VERSION);
            System.out.println("========================================");
            System.out.println("[INFO] Verified: In-Memory DataStore initialized with "
                    + app.trainService.getAllTrains().size() + " trains and "
                    + app.trainService.getAllStations().size() + " stations.");
            return;
        }

        app.runMainMenu();
    }

    public void runMainMenu() {
        boolean running = true;

        while (running) {
            ConsoleUtil.printHeader(APP_NAME);
            System.out.println("1. Register Passenger");
            System.out.println("2. Login");
            System.out.println("3. Search Trains");
            System.out.println("4. View Stations");
            System.out.println("5. Check PNR Status");
            System.out.println("6. Exit");
            ConsoleUtil.printDivider();

            int choice = ConsoleUtil.readInt("Enter your choice", 1, 6);

            if (choice == 1) {
                handleRegistration();
            } else if (choice == 2) {
                handleLogin();
            } else if (choice == 3) {
                handleTrainSearch();
            } else if (choice == 4) {
                handleViewStations();
            } else if (choice == 5) {
                handleCheckPnrStatus();
            } else if (choice == 6) {
                running = false;
                ConsoleUtil.printInfo("Thank you for using IRCTC Railway Reservation. Goodbye!");
            } else {
                ConsoleUtil.printError("Invalid selection. Please choose an option between 1 and 6.");
            }
        }
    }

    public void runPassengerMenu(Passenger passenger) {
        boolean loggedIn = true;

        while (loggedIn) {
            ConsoleUtil.printHeader("PASSENGER MENU (" + passenger.getUsername() + ")");
            System.out.println("1. Search Trains");
            System.out.println("2. Book Ticket");
            System.out.println("3. View My Bookings");
            System.out.println("4. Cancel Ticket");
            System.out.println("5. Check PNR Status");
            System.out.println("6. Logout");
            ConsoleUtil.printDivider();

            int choice = ConsoleUtil.readInt("Enter your choice", 1, 6);

            if (choice == 1) {
                handleTrainSearch();
            } else if (choice == 2) {
                handleBookTicket(passenger);
            } else if (choice == 3) {
                handleViewMyBookings(passenger);
            } else if (choice == 4) {
                handleCancelTicket();
            } else if (choice == 5) {
                handleCheckPnrStatus();
            } else if (choice == 6) {
                authService.logout();
                loggedIn = false;
                ConsoleUtil.printSuccess("Logged out successfully.");
            } else {
                ConsoleUtil.printError("Invalid option.");
            }
        }
    }

    public void runAdminMenu(Admin admin) {
        boolean loggedIn = true;

        while (loggedIn) {
            ConsoleUtil.printHeader("ADMIN MENU (" + admin.getUsername() + ")");
            System.out.println("1. Add Train");
            System.out.println("2. View All Trains");
            System.out.println("3. Add Station");
            System.out.println("4. View All Stations");
            System.out.println("5. View Bookings");
            System.out.println("6. Logout");
            ConsoleUtil.printDivider();

            int choice = ConsoleUtil.readInt("Enter your choice", 1, 6);

            if (choice == 1) {
                handleAddTrain();
            } else if (choice == 2) {
                handleViewAllTrains();
            } else if (choice == 3) {
                handleAddStation();
            } else if (choice == 4) {
                handleViewStations();
            } else if (choice == 5) {
                handleViewAllBookings();
            } else if (choice == 6) {
                authService.logout();
                loggedIn = false;
                ConsoleUtil.printSuccess("Admin logged out successfully.");
            } else {
                ConsoleUtil.printError("Invalid option.");
            }
        }
    }

    public void handleRegistration() {
        ConsoleUtil.printSubHeader("Passenger Registration");
        String username = ConsoleUtil.readString("Enter Username");
        String password = ConsoleUtil.readString("Enter Password");
        String fullName = ConsoleUtil.readString("Enter Full Name");
        String email = ConsoleUtil.readString("Enter Email");
        String phone = ConsoleUtil.readString("Enter 10-Digit Mobile");
        int age = ConsoleUtil.readInt("Enter Age", 1, 120);
        String gender = ConsoleUtil.readString("Enter Gender (Male/Female/Other)");

        try {
            Passenger p = authService.registerPassenger(username, password, fullName, email, phone, age, gender);
            ConsoleUtil.printSuccess("Registration successful! Welcome, " + p.getFullName() + ". Please login to proceed.");
        } catch (InputValidator.ValidationException e) {
            ConsoleUtil.printError("Registration Failed: " + e.getMessage());
        }
        ConsoleUtil.pressEnterToContinue();
    }

    public void handleLogin() {
        ConsoleUtil.printSubHeader("User Login");
        String username = ConsoleUtil.readString("Enter Username");
        String password = ConsoleUtil.readString("Enter Password");

        try {
            User user = authService.login(username, password);
            ConsoleUtil.printSuccess("Welcome back, " + user.getFullName() + " [" + user.getRole() + "]!");
            user.displayProfile();

            if (user instanceof Admin) {
                runAdminMenu((Admin) user);
            } else if (user instanceof Passenger) {
                runPassengerMenu((Passenger) user);
            }
        } catch (InputValidator.ValidationException e) {
            ConsoleUtil.printError("Login Failed: " + e.getMessage());
            ConsoleUtil.pressEnterToContinue();
        }
    }

    public void handleTrainSearch() {
        ConsoleUtil.printSubHeader("Search Trains by Route");
        String src = ConsoleUtil.readString("Enter Source Station Code (e.g., NDLS, BCT)");
        String dest = ConsoleUtil.readString("Enter Destination Station Code (e.g., HWH, BSB)");

        List<Train> results = trainService.searchTrains(src, dest);
        if (results.isEmpty()) {
            ConsoleUtil.printInfo("No trains found running directly from " + src.toUpperCase() + " to " + dest.toUpperCase() + ".");
        } else {
            System.out.println("\nMatching Trains (" + results.size() + " found):");
            System.out.printf("%-6s | %-24s | %-12s | %-6s -> %-6s%n", "Train#", "Train Name", "Type", "Source", "Dest");
            ConsoleUtil.printDivider();
            for (Train t : results) {
                System.out.printf("%-6s | %-24s | %-12s | %-6s -> %-6s%n",
                        t.getTrainNumber(), t.getTrainName(), t.getTrainType(), t.getSourceStation(), t.getDestinationStation());
            }

            String viewDetails = ConsoleUtil.readString("\nEnter Train Number to inspect route/seats (or press Enter to skip)");
            if (!viewDetails.isEmpty()) {
                trainService.displayTrainDetails(viewDetails);
            }
        }
        ConsoleUtil.pressEnterToContinue();
    }

    public void handleViewStations() {
        ConsoleUtil.printSubHeader("Railway Stations Directory");
        List<Station> stations = trainService.getAllStations();
        System.out.printf("%-6s | %-25s | %-18s | %s%n", "Code", "Station Name", "City", "Platforms");
        ConsoleUtil.printDivider();
        for (Station s : stations) {
            System.out.println(s);
        }
        ConsoleUtil.pressEnterToContinue();
    }

    public void handleViewAllStations() {
        handleViewStations();
    }

    public void handleCheckPnrStatus() {
        ConsoleUtil.printSubHeader("PNR Status Enquiry");
        String pnr = ConsoleUtil.readString("Enter 10-Digit PNR Number");

        Booking booking = bookingService.getPnrStatus(pnr);
        if (booking == null) {
            ConsoleUtil.printError("No booking record found for PNR: " + pnr);
        } else {
            Ticket ticket = new Ticket("TKT-" + pnr.substring(0, Math.min(6, pnr.length())), booking);
            ticket.printTicket();
        }
        ConsoleUtil.pressEnterToContinue();
    }

    public void handleBookTicket(Passenger passenger) {
        ConsoleUtil.printSubHeader("Book Railway Ticket");
        String trainNumber = ConsoleUtil.readString("Enter Train Number (e.g., 12301, 22436)");
        Train train = trainService.getTrainByNumber(trainNumber);
        if (train == null) {
            ConsoleUtil.printError("Train " + trainNumber + " does not exist.");
            ConsoleUtil.pressEnterToContinue();
            return;
        }

        trainService.displayTrainDetails(trainNumber);

        String fromStation = ConsoleUtil.readString("Enter Boarding Station Code");
        String toStation = ConsoleUtil.readString("Enter Destination Station Code");
        String travelClass = ConsoleUtil.readString("Enter Travel Class (SL / 3A / 2A / 1A)").toUpperCase();
        String travelDate = ConsoleUtil.readString("Enter Travel Date (YYYY-MM-DD)");

        int count = ConsoleUtil.readInt("Enter Number of Passengers (1-6)", 1, 6);
        List<BookingService.PassengerInput> passengerList = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            System.out.println("\n--- Passenger " + i + " Details ---");
            String pName = ConsoleUtil.readString("Full Name");
            int pAge = ConsoleUtil.readInt("Age", 1, 120);
            String pGender = ConsoleUtil.readString("Gender (Male/Female/Other)");
            passengerList.add(new BookingService.PassengerInput(pName, pAge, pGender));
        }

        try {
            Ticket ticket = bookingService.bookTicket(passenger.getUsername(), trainNumber,
                    travelClass, travelDate, fromStation, toStation, passengerList);
            ConsoleUtil.printSuccess("Booking Confirmed successfully!");
            ticket.printTicket();
        } catch (BookingService.BookingException e) {
            ConsoleUtil.printError("Booking Failed: " + e.getMessage());
        }
        ConsoleUtil.pressEnterToContinue();
    }

    public void handleViewMyBookings(Passenger passenger) {
        ConsoleUtil.printSubHeader("My Booking History");
        List<Booking> bookings = bookingService.getBookingsByUser(passenger.getUsername());
        if (bookings.isEmpty()) {
            ConsoleUtil.printInfo("You have not booked any tickets yet.");
        } else {
            for (Booking b : bookings) {
                System.out.println(b);
            }
        }
        ConsoleUtil.pressEnterToContinue();
    }

    public void handleCancelTicket() {
        ConsoleUtil.printSubHeader("Ticket Cancellation");
        String pnr = ConsoleUtil.readString("Enter 10-Digit PNR to cancel");

        try {
            boolean cancelled = bookingService.cancelTicket(pnr);
            if (cancelled) {
                ConsoleUtil.printSuccess("Ticket for PNR " + pnr + " has been cancelled. Seats released to inventory.");
            }
        } catch (BookingService.BookingException e) {
            ConsoleUtil.printError("Cancellation Failed: " + e.getMessage());
        }
        ConsoleUtil.pressEnterToContinue();
    }

    public void handleAddTrain() {
        ConsoleUtil.printSubHeader("Admin: Register New Train");
        String trainNumber = ConsoleUtil.readString("Enter 5-Digit Train Number");
        String trainName = ConsoleUtil.readString("Enter Train Name");
        String trainType = ConsoleUtil.readString("Enter Train Type (EXPRESS / SUPERFAST / RAJDHANI)");
        String src = ConsoleUtil.readString("Enter Source Station Code");
        String dest = ConsoleUtil.readString("Enter Destination Station Code");

        Route route = new Route("R-" + trainNumber, src, dest);
        route.addStop(src, 0.0, "START", "08:00");
        route.addStop(dest, 500.0, "16:00", "END");

        Train newTrain = new Train(trainNumber, trainName, trainType, src, dest, route);
        newTrain.addCoach(new Coach("S1", "SL", 6, 4));
        newTrain.addCoach(new Coach("B1", "3A", 6, 4));

        try {
            trainService.addTrain(newTrain);
            ConsoleUtil.printSuccess("Train " + trainNumber + " (" + trainName + ") added successfully with 48 seats.");
        } catch (InputValidator.ValidationException e) {
            ConsoleUtil.printError("Failed to add train: " + e.getMessage());
        }
        ConsoleUtil.pressEnterToContinue();
    }

    public void handleViewAllTrains() {
        ConsoleUtil.printSubHeader("Admin: All Registered Trains");
        List<Train> trains = trainService.getAllTrains();
        System.out.printf("%-6s | %-24s | %-12s | %-6s -> %-6s | %s%n",
                "Train#", "Train Name", "Type", "Source", "Dest", "Total Seats");
        ConsoleUtil.printDivider();
        for (Train t : trains) {
            System.out.println(t);
        }
        ConsoleUtil.pressEnterToContinue();
    }

    public void handleAddStation() {
        ConsoleUtil.printSubHeader("Admin: Register New Station");
        String code = ConsoleUtil.readString("Enter Station Code (e.g. GKP, LKO)");
        String name = ConsoleUtil.readString("Enter Station Name");
        String city = ConsoleUtil.readString("Enter City");
        int platforms = ConsoleUtil.readInt("Enter Number of Platforms", 1, 50);

        Station station = new Station(code, name, city, platforms);
        try {
            trainService.addStation(station);
            ConsoleUtil.printSuccess("Station " + code + " (" + name + ") added successfully.");
        } catch (InputValidator.ValidationException e) {
            ConsoleUtil.printError("Failed to add station: " + e.getMessage());
        }
        ConsoleUtil.pressEnterToContinue();
    }

    public void handleViewAllBookings() {
        ConsoleUtil.printSubHeader("Admin: System-Wide Reservations");
        List<Booking> allBookings = bookingService.getAllBookings();
        if (allBookings.isEmpty()) {
            ConsoleUtil.printInfo("No bookings currently in system.");
        } else {
            System.out.println("Total Reservations: " + allBookings.size());
            ConsoleUtil.printDivider();
            for (Booking b : allBookings) {
                System.out.println(b);
            }
        }
        ConsoleUtil.pressEnterToContinue();
    }
}
