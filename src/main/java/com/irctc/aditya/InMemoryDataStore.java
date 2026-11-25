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
import com.irctc.jyot.TrainSchedule;

import java.util.*;

public class InMemoryDataStore {

    private static InMemoryDataStore instance;

    private final Map<String, User> userMap;
    private final Map<String, Train> trainMap;
    private final Map<String, Station> stationMap;
    private final List<TrainSchedule> schedules;
    private final Map<String, Booking> bookingMap;
    private final Queue<String> waitingListQueue;
    private final Set<String> registeredUsernames;
    private final Set<String> activePnrs;
    private final Deque<String> recentSearches;

    public InMemoryDataStore() {
        this.userMap = new HashMap<>();
        this.trainMap = new LinkedHashMap<>();
        this.stationMap = new TreeMap<>();
        this.schedules = new ArrayList<>();
        this.bookingMap = new LinkedHashMap<>();
        this.waitingListQueue = new LinkedList<>();
        this.registeredUsernames = new HashSet<>();
        this.activePnrs = new LinkedHashSet<>();
        this.recentSearches = new ArrayDeque<>();
        initializeSeedData();
    }

    public static synchronized InMemoryDataStore getInstance() {
        if (instance == null) {
            instance = new InMemoryDataStore();
        }
        return instance;
    }

    public void initializeSeedData() {
        addStation(new Station("NDLS", "New Delhi", "New Delhi", 16));
        addStation(new Station("CNB", "Kanpur Central", "Kanpur", 10));
        addStation(new Station("PRYJ", "Prayagraj Jn", "Prayagraj", 10));
        addStation(new Station("DDU", "Pt. Deen Dayal Upadhyaya", "Mughalsarai", 8));
        addStation(new Station("BSB", "Varanasi Jn", "Varanasi", 9));
        addStation(new Station("HWH", "Howrah Jn", "Kolkata", 23));
        addStation(new Station("BCT", "Mumbai Central", "Mumbai", 5));
        addStation(new Station("BRC", "Vadodara Jn", "Vadodara", 7));

        Route r1 = new Route("R01", "NDLS", "BSB");
        r1.addStop("NDLS", 0.0, "START", "06:00");
        r1.addStop("CNB", 440.0, "10:10", "10:15");
        r1.addStop("PRYJ", 635.0, "12:15", "12:20");
        r1.addStop("DDU", 788.0, "13:55", "14:00");
        r1.addStop("BSB", 806.0, "14:40", "END");

        Route r2 = new Route("R02", "NDLS", "HWH");
        r2.addStop("NDLS", 0.0, "START", "16:55");
        r2.addStop("CNB", 440.0, "21:35", "21:40");
        r2.addStop("PRYJ", 635.0, "23:45", "23:47");
        r2.addStop("DDU", 788.0, "01:45", "01:55");
        r2.addStop("HWH", 1445.0, "09:55", "END");

        Route r3 = new Route("R03", "BCT", "NDLS");
        r3.addStop("BCT", 0.0, "START", "17:00");
        r3.addStop("BRC", 392.0, "21:50", "21:55");
        r3.addStop("NDLS", 1384.0, "08:35", "END");

        Train t1 = new Train("12301", "Howrah Rajdhani", "RAJDHANI", "NDLS", "HWH", r2);
        t1.addCoach(new Coach("B1", "3A", 6, 4));
        t1.addCoach(new Coach("B2", "3A", 6, 4));
        t1.addCoach(new Coach("A1", "2A", 6, 4));
        t1.addCoach(new Coach("S1", "SL", 6, 4));
        addTrain(t1);

        Train t2 = new Train("22436", "Vande Bharat Express", "VANDE_BHARAT", "NDLS", "BSB", r1);
        t2.addCoach(new Coach("C1", "3A", 6, 4));
        t2.addCoach(new Coach("C2", "3A", 6, 4));
        t2.addCoach(new Coach("E1", "1A", 6, 4));
        addTrain(t2);

        Train t3 = new Train("12951", "Mumbai Rajdhani", "RAJDHANI", "BCT", "NDLS", r3);
        t3.addCoach(new Coach("A1", "2A", 6, 4));
        t3.addCoach(new Coach("B1", "3A", 6, 4));
        t3.addCoach(new Coach("S1", "SL", 6, 4));
        addTrain(t3);

        Train t4 = new Train("12002", "Bhopal Shatabdi", "SHATABDI", "NDLS", "CNB", r1);
        t4.addCoach(new Coach("C1", "3A", 6, 4));
        t4.addCoach(new Coach("E1", "1A", 6, 4));
        addTrain(t4);

        schedules.add(new TrainSchedule("SCH-01", t1, "2026-11-26", "16:55", "09:55", t1.getTotalSeats()));
        schedules.add(new TrainSchedule("SCH-02", t2, "2026-11-26", "06:00", "14:40", t2.getTotalSeats()));
        schedules.add(new TrainSchedule("SCH-03", t3, "2026-11-26", "17:00", "08:35", t3.getTotalSeats()));
        schedules.add(new TrainSchedule("SCH-04", t4, "2026-11-26", "06:15", "11:20", t4.getTotalSeats()));

        Admin admin = new Admin("ADM001", "admin", "admin123", "Chief Commercial Manager",
                "admin@irctc.co.in", "9811002233", "NR-OPS-01", "Operations");
        addUser(admin);

        Passenger p1 = new Passenger("USR101", "rahul", "pass123", "Rahul Sharma",
                "rahul@example.com", "9876543210", 28, "Male");
        addUser(p1);

        Passenger p2 = new Passenger("USR102", "priya", "pass123", "Priya Patel",
                "priya@example.com", "9876501234", 62, "Female");
        addUser(p2);

        String samplePnr = "8210345678";
        Booking sampleBooking = new Booking(samplePnr, "rahul", t1, "3A", "2026-11-26",
                "NDLS", "HWH", 2023.0, "CONFIRMED");
        Coach b1 = t1.getCoach("B1");
        if (b1 != null) {
            Seat s1 = b1.findNextAvailableSeat();
            if (s1 != null) {
                s1.book("Rahul Sharma", 28, "Male");
                sampleBooking.addAllocatedSeat(s1, "Rahul Sharma");
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

    public void addStation(Station station) {
        if (station != null) {
            stationMap.put(station.getStationCode(), station);
        }
    }

    public Station getStation(String stationCode) {
        if (stationCode == null) return null;
        return stationMap.get(stationCode.trim().toUpperCase());
    }

    public Collection<Station> getAllStations() {
        return stationMap.values();
    }

    public List<TrainSchedule> getSchedules() {
        return schedules;
    }

    public void addBooking(Booking booking) {
        if (booking != null) {
            bookingMap.put(booking.getPnr(), booking);
            activePnrs.add(booking.getPnr());
        }
    }

    public Booking getBooking(String pnr) {
        if (pnr == null) return null;
        return bookingMap.get(pnr.trim());
    }

    public Collection<Booking> getAllBookings() {
        return bookingMap.values();
    }

    public void removeActivePnr(String pnr) {
        if (pnr != null) {
            activePnrs.remove(pnr.trim());
        }
    }

    public void addToWaitingList(String passengerEntry) {
        waitingListQueue.offer(passengerEntry);
    }

    public String pollWaitingList() {
        return waitingListQueue.poll();
    }

    public Queue<String> getWaitingListQueue() {
        return waitingListQueue;
    }

    public void recordSearch(String searchEntry) {
        recentSearches.offerFirst(searchEntry);
        if (recentSearches.size() > 10) {
            recentSearches.pollLast();
        }
    }

    public Deque<String> getRecentSearches() {
        return recentSearches;
    }
}
