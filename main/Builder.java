package main;

import passenger.HandBaggage;
import passenger.Passenger;
import rooms.FederalPoliceOffice;
import staff.FederalPoliceOfficer;
import staff.HouseKeeper;
import staff.Inspector;
import staff.Supervisor;
import staff.Technician;
import utility.Configuration;
import utility.ProfileType;
import utility.ProhibitedItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;

public class Builder {
    public static void main(String[] args) {
        try {
            Simulation sim = build();
            System.out.println("Fertig!");
            sim.run();
            System.out.println("Abschluss!");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static Simulation build() {
        return new Simulation(Configuration.getCurrentAlg(), createInspectors(), createSuperVisor(), createOffice(), createTechnician(), createHouseKeeper(), readPassengers());
    }

    private static Supervisor createSuperVisor() {
        return new Supervisor("Jodie Foster", LocalDate.of(1962, 11, 19), ProfileType.S, 1962, LocalDate.now().plusYears(2), false, false);
    }

    private static Queue<Passenger> readPassengers() {
        Queue<Passenger> passengers = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Builder.class.getClassLoader().getResourceAsStream(Configuration.DATA_PATH)))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] lineArr = line.split(";", 3);
                HandBaggage[] bags = new HandBaggage[Integer.parseInt(lineArr[1])];

                if (!lineArr[2].equals("-")) {
                    String[] prohibited = lineArr[2].substring(1, lineArr[2].length() - 1).split(lineArr[2].contains(";") ? ";" : ",");
                    if (lineArr[2].contains(";")) {
                        for (int i = 0; i < prohibited.length; i++) {
                            String[] test = prohibited[i].split(",");
                            bags[Integer.parseInt(prohibited[i].split(",")[1]) - 1] = getBaggage(prohibited[i].split(",")[0].charAt(0), Integer.parseInt(prohibited[i].split(",")[2]));
                        }
                    } else {
                        bags[Integer.parseInt(prohibited[1]) - 1] = getBaggage(prohibited[0].charAt(0), Integer.parseInt(prohibited[2]));
                    }
                }
                for (int i = 0; i < bags.length; i++) {
                    if (bags[i] == null) {
                        bags[i] = new HandBaggage();
                    }
                }
                passengers.add(new Passenger(lineArr[0], bags));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        java.util.List<Passenger> list = new java.util.ArrayList<>(passengers);
        return passengers;
    }

    private static HandBaggage getBaggage(char type, int pos) {
        return switch (type) {
            case 'K' -> new HandBaggage(ProhibitedItem.KNIFE, pos);
            case 'W' -> new HandBaggage(ProhibitedItem.GLOCK7, pos);
            case 'E' -> new HandBaggage(ProhibitedItem.EXPLOSIVE, pos);
            default -> new HandBaggage();
        };
    }

    private static Inspector[] createInspectors() {
        Inspector[] inspectors = new Inspector[3];
        inspectors[0] = new Inspector("Clint Eastwood", LocalDate.of(1930, 5,31), ProfileType.I, 1930, LocalDate.now().plusYears(2), true);
        inspectors[1] = new Inspector("Natalie Portman", LocalDate.of(1981, 6,9), ProfileType.I, 1981, LocalDate.now().plusYears(2), false);
        inspectors[2] = new Inspector("Bruce Willis", LocalDate.of(1955, 3,19), ProfileType.I, 1955, LocalDate.now().plusYears(2), true);
        return inspectors;
    }

    private static FederalPoliceOffice createOffice() {
        FederalPoliceOffice office = new FederalPoliceOffice();
        office.addOfficer(new FederalPoliceOfficer("Wesley Snipes", LocalDate.of(1962, 7, 31), ProfileType.O, 1962, LocalDate.now().plusYears(2), "O1", office));
        office.addOfficer(new FederalPoliceOfficer("Toto", LocalDate.of(1969, 1, 1), ProfileType.O, 1969, LocalDate.now().plusYears(2), "O2", office));
        office.addOfficer(new FederalPoliceOfficer("Harry", LocalDate.of(1969, 1, 1), ProfileType.O, 1969, LocalDate.now().plusYears(2), "O3", office));
        return office;
    }

    private static Technician createTechnician() {
        return new Technician("Jason Statham", LocalDate.of(1967, 7,26), ProfileType.T, 1967, LocalDate.now().plusYears(2));
    }

    private static HouseKeeper createHouseKeeper() {
        return new HouseKeeper("Jason Clarke", LocalDate.of(1969, 7,17), ProfileType.K, 1969, LocalDate.now().plusYears(2));
    }
}
