
package com.irctc.aditya;

import com.irctc.divi.Admin;
import com.irctc.divi.Passenger;
import com.irctc.divi.User;
import com.irctc.harbin.Booking;
import com.irctc.harbin.Coach;
import com.irctc.harbin.Seat;
import com.irctc.jyot.Route;
import com.irctc.jyot.Station;
import com.irctc.jyot.Train;

import java.util.*;

public class InMemoryDataStore {

    private static InMemoryDataStore instance;

    private final Map<String, User> userMap;
    private final Map<String, Train> trainMap;
    private final Map<String, Station> stationMap;
    private final Map<String, Booking> bookingMap;
    private final Queue<String> waitingListQueue;
    private final Set<String> registeredUsernames;

    public InMemoryDataStore() {
        this.userMap = new HashMap<>();
        this.trainMap = new LinkedHashMap<>();
        this.stationMap = new TreeMap<>();
        this.bookingMap = new LinkedHashMap<>();
        this.waitingListQueue = new LinkedList<>();
        this.registeredUsernames = new HashSet<>();

        initializeSeedData();
    }

    public static synchronized InMemoryDataStore getInstance() {
        if (instance == null) {
            instance = new InMemoryDataStore();
        }
        return instance;
    }

    public void initializeSeedData() {

        // Stations
        addStation(new Station("Chandigarh Railway Station", "Chandigarh", 5));
        addStation(new Station("New Delhi", "New Delhi", 16));
        addStation(new Station("Jaipur Junction", "Jaipur", 8));
        addStation(new Station("Mumbai Central", "Mumbai", 5));
        addStation(new Station("Pune Junction", "Pune", 6));
        addStation(new Station("Hyderabad Deccan", "Hyderabad", 6));
        addStation(new Station("KSR Bengaluru", "Bengaluru", 10));
        addStation(new Station("Agra Cantt", "Agra", 6));
        addStation(new Station("Bhopal Junction", "Bhopal", 6));
        addStation(new Station("Lucknow Charbagh", "Lucknow", 9));
        addStation(new Station("Varanasi Junction", "Varanasi", 9));
        addStation(new Station("Howrah Junction", "Kolkata", 23));
        addStation(new Station("Patna Junction", "Patna", 7));
        addStation(new Station("Chennai Central", "Chennai", 12));

        // Route 1: Chandigarh to Mumbai
        Route r1 = new Route();
        r1.addStop("Chandigarh", 0.0, "START", "06:00");
        r1.addStop("New Delhi", 250.0, "10:00", "10:15");
        r1.addStop("Jaipur", 550.0, "14:00", "14:15");
        r1.addStop("Mumbai", 1350.0, "23:00", "END");

        // Route 2: Mumbai to Bengaluru
        Route r2 = new Route();
        r2.addStop("Mumbai", 0.0, "START", "07:00");
        r2.addStop("Pune", 150.0, "10:00", "10:15");
        r2.addStop("Hyderabad", 710.0, "18:00", "18:15");
        r2.addStop("Bengaluru", 980.0, "23:00", "END");

        // Route 3: Delhi to Bengaluru
        Route r3 = new Route();
        r3.addStop("New Delhi", 0.0, "START", "06:00");
        r3.addStop("Agra", 230.0, "09:00", "09:15");
        r3.addStop("Bhopal", 700.0, "15:00", "15:15");
        r3.addStop("Bengaluru", 2150.0, "08:00", "END");

        // Route 4: Delhi to Kolkata
        Route r4 = new Route();
        r4.addStop("New Delhi", 0.0, "START", "07:00");
        r4.addStop("Lucknow", 500.0, "13:00", "13:15");
        r4.addStop("Varanasi", 680.0, "17:00", "17:15");
        r4.addStop("Kolkata", 1450.0, "06:00", "END");

        // Route 5: Kolkata to Delhi
        Route r5 = new Route();
        r5.addStop("Kolkata", 0.0, "START", "06:00");
        r5.addStop("Patna", 530.0, "13:00", "13:15");
        r5.addStop("Varanasi", 780.0, "18:00", "18:15");
        r5.addStop("New Delhi", 1450.0, "07:00", "END");

        // Route 6: Bengaluru to Mumbai
        Route r6 = new Route();
        r6.addStop("Bengaluru", 0.0, "START", "06:00");
        r6.addStop("Chennai", 350.0, "11:00", "11:15");
        r6.addStop("Hyderabad", 700.0, "17:00", "17:15");
        r6.addStop("Mumbai", 980.0, "23:00", "END");

        // Train 1: Chandigarh to Mumbai
        Train t1 = new Train(
                "12011", "Chandigarh Mumbai Express", "EXPRESS",
                "Chandigarh", "Mumbai", r1
        );
        t1.addCoach(new Coach("B1", "3A", 6, 4));
        t1.addCoach(new Coach("S1", "SL", 6, 4));
        addTrain(t1);

        // Train 2: Mumbai to Bengaluru
        Train t2 = new Train(
                "12012", "Mumbai Bengaluru Express", "EXPRESS",
                "Mumbai", "Bengaluru", r2
        );
        t2.addCoach(new Coach("B1", "3A", 6, 4));
        t2.addCoach(new Coach("S1", "SL", 6, 4));
        addTrain(t2);

        // Train 3: Delhi to Bengaluru
        Train t3 = new Train(
                "12013", "Delhi Bengaluru Express", "EXPRESS",
                "New Delhi", "Bengaluru", r3
        );
        t3.addCoach(new Coach("B1", "3A", 6, 4));
        t3.addCoach(new Coach("S1", "SL", 6, 4));
        addTrain(t3);

        // Train 4: Delhi to Kolkata
        Train t4 = new Train(
                "12014", "Delhi Kolkata Express", "EXPRESS",
                "New Delhi", "Kolkata", r4
        );
        t4.addCoach(new Coach("B1", "3A", 6, 4));
        t4.addCoach(new Coach("S1", "SL", 6, 4));
        addTrain(t4);

        // Train 5: Kolkata to Delhi
        Train t5 = new Train(
                "12015", "Kolkata Delhi Express", "EXPRESS",
                "Kolkata", "New Delhi", r5
        );
        t5.addCoach(new Coach("B1", "3A", 6, 4));
        t5.addCoach(new Coach("S1", "SL", 6, 4));
        addTrain(t5);

        // Train 6: Bengaluru to Mumbai
        Train t6 = new Train(
                "12016", "Bengaluru Mumbai Express", "EXPRESS",
                "Bengaluru", "Mumbai", r6
        );
        t6.addCoach(new Coach("B1", "3A", 6, 4));
        t6.addCoach(new Coach("S1", "SL", 6, 4));
        addTrain(t6);

        // Admin account
        Admin admin = new Admin(
                "ADM001", "admin", "admin123",
                "Chief Commercial Manager",
                "admin@irctc.co.in", "9811002233",
                "NR-OPS-01", "Operations"
        );
        addUser(admin);

        // Passenger accounts
        Passenger p1 = new Passenger(
                "USR101", "rahul", "pass123", "Rahul Sharma",
                "rahul@example.com", "9876543210", 28, "Male"
        );
        addUser(p1);

        Passenger p2 = new Passenger(
                "USR102", "priya", "pass123", "Priya Patel",
                "priya@example.com", "9876501234", 62, "Female"
        );
        addUser(p2);

        // Sample booking using Train 4
        String samplePnr = "8210345678";

        Booking sampleBooking = new Booking(
                samplePnr, "rahul", t4, "3A", "2026-11-26",
                "New Delhi", "Kolkata", 1450.0, "CONFIRMED"
        );

        Coach b1 = t4.getCoach("B1");

        if (b1 != null) {
            Seat s1 = b1.findNextAvailableSeat();

            if (s1 != null) {
                s1.book("Rahul Sharma");
                sampleBooking.addAllocatedSeat(s1);
            }
        }

        addBooking(sampleBooking);
        p1.addPnr(samplePnr);
    }

    public void addUser(User user) {
        if (user != null) {
            userMap.put(user.getUsername().toLowerCase(), user);
            registeredUsernames.add(user.getUsername().toLowerCase());
        }
    }

    public User getUser(String username) {
        if (username == null) return null;
        return userMap.get(username.trim().toLowerCase());
    }

    public boolean isUsernameTaken(String username) {
        if (username == null) return false;
        return registeredUsernames.contains(username.trim().toLowerCase());
    }

    public int getUserCount() {
        return userMap.size();
    }

    public void addTrain(Train train) {
        if (train != null) {
            trainMap.put(train.getTrainNumber(), train);
        }
    }

    public Train getTrain(String trainNumber) {
        if (trainNumber == null) return null;
        return trainMap.get(trainNumber.trim());
    }

    public Collection<Train> getAllTrains() {
        return trainMap.values();
    }

    // Stations are stored using city names
    public void addStation(Station station) {
        if (station != null) {
            stationMap.put(station.getCity().trim().toLowerCase(), station);
        }
    }

    public Station getStation(String city) {
        if (city == null) return null;
        return stationMap.get(city.trim().toLowerCase());
    }

    public Collection<Station> getAllStations() {
        return stationMap.values();
    }

    public void addBooking(Booking booking) {
        if (booking != null) {
            bookingMap.put(booking.getPnr(), booking);
        }
    }

    public Booking getBooking(String pnr) {
        if (pnr == null) return null;
        return bookingMap.get(pnr.trim());
    }

    public Collection<Booking> getAllBookings() {
        return bookingMap.values();
    }

    public void addToWaitingList(String passengerEntry) {
        waitingListQueue.offer(passengerEntry);
    }

    public String pollWaitingList() {
        return waitingListQueue.poll();
    }

}
