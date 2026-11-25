# IRCTC Railway Reservation System

A clean, menu-driven Railway Reservation Management System built in **Java 17** using **in-memory Java Collections** and standard OOP principles. Designed specifically for academic presentation and college viva defense aligned strictly with the university *Programming in Java* syllabus.

---

## Table of Contents

- [Overview](#overview)
- [Team Division (Exactly 5 Core Files Each)](#team-division-exactly-5-core-files-each)
- [Syllabus Alignment & Concepts Demonstrated](#syllabus-alignment--concepts-demonstrated)
- [Collections & Data Structures Used](#collections--data-structures-used)
- [Menu Flow & Features](#menu-flow--features)
- [How to Build and Run](#how-to-build-and-run)
- [Pre-Loaded Sample Accounts](#pre-loaded-sample-accounts)

---

## Overview

The IRCTC Railway Reservation System is a lightweight, fully functional console application that models passenger ticket reservation, coach seat layouts, timetable searching, fare calculation, 10-digit PNR generation, cancellation, and administrative station/train management.

- **Zero External Dependencies**: Runs entirely in pure Java SE without external databases, SQL drivers, or third-party frameworks.
- **In-Memory Storage**: Uses Java Collections in `InMemoryDataStore.java` that initialize with realistic Indian Railways seed data on startup.
- **No Multithreading**: Clean, single-threaded execution focused on straightforward control flow.

---

## Team Division (Exactly 5 Core Files Each)

The project consists of **exactly 20 core application `.java` files** divided evenly and fairly among four team members:

```text
src/main/java/com/irctc/
│
├── divi/                          # Member 1: Divi (Team Lead)
│   ├── Main.java
│   ├── User.java
│   ├── Passenger.java
│   ├── Admin.java
│   └── AuthService.java
│
├── jyot/                          # Member 2: Jyot (Train Management)
│   ├── Train.java
│   ├── Station.java
│   ├── Route.java
│   ├── TrainSchedule.java
│   └── TrainService.java
│
├── aditya/                        # Member 3: Aditya (Data Management & Utilities)
│   ├── InMemoryDataStore.java
│   ├── FareCalculator.java
│   ├── PnrGenerator.java
│   ├── InputValidator.java
│   └── ConsoleUtil.java
│
└── harbin/                        # Member 4: Harbin (Booking Management)
    ├── Seat.java
    ├── Coach.java
    ├── Booking.java
    ├── Ticket.java
    └── BookingService.java
```

---

## Syllabus Alignment & Concepts Demonstrated

| Syllabus Module | Concepts Covered | Exact Implementation in Code |
| :--- | :--- | :--- |
| **Lectures 1–5: OOP** | Classes, Objects, Inheritance, Polymorphism, Abstraction, `this`, `super` | `User` abstract class with abstract `displayProfile()`; `Passenger` and `Admin` subclasses overriding methods with `super()` constructor chaining. |
| **Lectures 6–9: Arrays & Strings** | Multidimensional Arrays, String Formatting, `StringBuilder`, Wrapper Classes | `Seat[][] seatGrid` (2D array in `Coach.java` for row-column seat layout); `StringBuilder` and `String.format()` in `Ticket.java` and `ConsoleUtil.java`; `Integer.parseInt()` wrapper conversions. |
| **Lectures 10–12: Exception Handling** | Checked vs Unchecked Exceptions, `try-catch-finally`, Custom Exceptions | Custom checked exceptions `ValidationException` (in `InputValidator.java`) and `BookingException` (in `BookingService.java`) with defensive `try-catch` blocks. |
| **Lectures 13–15: Integrated Practice** | Menu-driven console UI, input validation, robust scanning | Reusable validation in `InputValidator.java`; `while` loops and `if-else-if` statements for menu navigation. |
| **Lectures 16–19: Collections Framework** | `ArrayList`, `HashSet`, `LinkedHashSet` | `ArrayList` for schedules & passenger lists; `HashSet` for username uniqueness; `LinkedHashSet` for active PNR tracking. |
| **Lectures 20–22: Maps & Queues** | `HashMap`, `LinkedHashMap`, `TreeMap`, `Queue`, `Deque` | `HashMap` for user credentials; `LinkedHashMap` for trains & bookings; `TreeMap` for alphabetized station lookup; `Queue` (`LinkedList`) for FIFO waiting list; `Deque` (`ArrayDeque`) for recent search history. |
| **Lectures 23–25: Sorting** | `Comparable`, `Collections.sort` | `Station` implements `Comparable<Station>` (sorted by code); `Train` implements `Comparable<Train>` (sorted by number); `Collections.sort` applied to train search results. |

---

## Collections & Data Structures Used

In `InMemoryDataStore.java`, each collection has a genuine purpose:

- **`Map<String, User> userMap` (`HashMap`)**: Fast $O(1)$ username lookup for authentication.
- **`Map<String, Train> trainMap` (`LinkedHashMap`)**: Preserves train catalog registration order.
- **`Map<String, Station> stationMap` (`TreeMap`)**: Automatically keeps railway stations in alphabetical order by station code.
- **`List<TrainSchedule> schedules` (`ArrayList`)**: Fast indexed access to timetable schedules.
- **`Map<String, Booking> bookingMap` (`LinkedHashMap`)**: Instant lookup of booking records by 10-digit PNR.
- **`Queue<String> waitingListQueue` (`LinkedList`)**: FIFO queue holding waitlisted passengers when all coach seats are occupied.
- **`Set<String> registeredUsernames` (`HashSet`)**: Ensures zero duplicate usernames during registration.
- **`Set<String> activePnrs` (`LinkedHashSet`)**: Tracks active uncancelled PNRs.
- **`Deque<String> recentSearches` (`ArrayDeque`)**: Double-ended queue for recent route browsing history.
- **`Seat[][] seatGrid` (2D Array in `Coach.java`)**: Models the row $\times$ column geometry of physical train coaches.

---

## Menu Flow & Features

### 1. Main Menu
```text
========================================
       IRCTC RAILWAY RESERVATION
========================================
1. Register Passenger
2. Login
3. Search Trains
4. View Stations
5. Check PNR Status
6. Exit
Enter your choice:
```

### 2. Passenger Menu (Post-Login)
```text
========================================
             PASSENGER MENU
========================================
1. Search Trains
2. Book Ticket
3. View My Bookings
4. Cancel Ticket
5. Check PNR Status
6. Logout
Enter your choice:
```

### 3. Admin Menu (Post-Login)
```text
========================================
                ADMIN MENU
========================================
1. Add Train
2. View All Trains
3. Add Station
4. View All Stations
5. View Bookings
6. Logout
Enter your choice:
```

---

## How to Build and Run

### 1. Prerequisites
- **JDK 17** or higher (`java -version`, `javac -version`)

### 2. Compile with standard `javac`
Compile all 20 source files across team packages into a `bin` directory:
```bash
javac -d bin src/main/java/com/irctc/*/*.java
```

### 3. Run the Application
Launch the menu-driven application:
```bash
java -cp bin com.irctc.divi.Main
```

---

## Pre-Loaded Sample Accounts

The application starts pre-populated with sample accounts:

| Role | Username | Password | Notes |
| :--- | :--- | :--- | :--- |
| **Admin** | `admin` | `admin123` | Has access to Admin Menu (Add Train, Add Station, View Bookings) |
| **Passenger** | `rahul` | `pass123` | Pre-booked confirmed ticket on Train 12301 (**PNR: `8210345678`**) |
| **Passenger** | `priya` | `pass123` | Senior citizen passenger (qualifies for age concession) |
