package main;

import baggageScanner.Algorithm;
import baggageScanner.BaggageScanner;
import baggageScanner.Tray;
import passenger.HandBaggage;
import passenger.Passenger;
import rooms.CheckRoom;
import rooms.FederalPoliceOffice;
import staff.*;
import utility.*;

import java.util.LinkedList;
import java.util.Queue;

public class Simulation {
    private final Inspector[] inspectors;
    private final FederalPoliceOffice federalPoliceOffice;
    private final Supervisor supervisor;
    private final Technician technician;
    private final HouseKeeper houseKeeper;
    private Queue<Passenger> passengers = new LinkedList<>();
    private final FederalPoliceOfficer[] officers = new FederalPoliceOfficer[3];

    private final BaggageScanner baggageScanner;
    private Queue<Tray> trays;
    private Queue<HandBaggage> cleanBags;

    private Passenger currentPassenger;

    public Simulation(Algorithm algorithm, Inspector[] inspectors, Supervisor supervisor, FederalPoliceOffice office, Technician technician, HouseKeeper houseKeeper, Queue<Passenger> passengers) {
        this.inspectors = inspectors;
        this.federalPoliceOffice = office;
        for (int i = 0; i < officers.length; i++) {
            officers[i] = office.getOfficer(i);
        }
        this.supervisor = supervisor;
        this.technician = technician;
        this.houseKeeper = houseKeeper;

        this.baggageScanner = new BaggageScanner(algorithm, technician, inspectors[0], inspectors[1], inspectors[2], supervisor, office);
        this.passengers = passengers;
    }

    public void startScanner() {
        baggageScanner.getSupervision().getSupervisor().pressPowerButton();
    }

    public void activateScanner() {
        baggageScanner.getOpStation().activateBaggageScanner(inspectors[1].getBirthDate().getYear());
    }

    public void run() {
        startScanner();
        activateScanner();

        while (!passengers.isEmpty()) {
            currentPassenger = passengers.poll();
            while (currentPassenger.hasBaggage()) {
                baggageScanner.getRoller().fillTray(currentPassenger.getNextBaggage());
            }
            while (!baggageScanner.getRoller().getTrays().isEmpty()) {
                inspectors[0].pushTray();
                inspectors[1].pressButtonRight();
                Result res = inspectors[1].pressRectangleButton();
                while (res != Result.CLEAN) {
                    if (res == Result.KNIFE) {
                        baggageScanner.report(res.getPosition());
                        inspectors[1].pressButtonLeft();
                        res = inspectors[1].pressRectangleButton();
                    } else if (res == Result.EXPLOSIVE || res == Result.WEAPON) {
                        inspectors[1].pressAlarm();
                        Passenger p = baggageScanner.getBelt().getTracks()[0].removeFirstBaggage().getOwner();
                        federalPoliceOffice.getOfficer(0).arrest(p);
                        if (res == Result.WEAPON) {
                            CheckRoom<IPeople> room = new CheckRoom<>();
                            room.fillRoom(officers[0], officers[1], officers[2], supervisor);
                            officers[0].controlBaggage(res.getPosition(), baggageScanner.getBelt().getTracks()[0].getFirstTray());
                            officers[0].giveWeapon(officers[2], ProhibitedItem.GLOCK7);
                            while (!baggageScanner.getRoller().getTrays().isEmpty()) {
                                inspectors[1].pressButtonLeft();
                                res = inspectors[1].pressRectangleButton();
                                if (res.isProhibited()) {
                                    officers[0].controlBaggage(res.getPosition(), baggageScanner.getBelt().getTracks()[0].getFirstTray());
                                }
                                inspectors[2].giveBaggageToOfficer(officers[2], baggageScanner.getBelt().getTracks()[0].removeFirstBaggage());
                            }
                            officers[1].leave();
                            officers[2].leave();
                            room.clearRoom();
                        } else {
                            Robot robot = federalPoliceOffice.getRobot();
                            TestStripe stripe = new TestStripe();
                            inspectors[2].swipe(stripe, baggageScanner.getBelt().getTracks()[0].getFirstTray().getHandBaggage());
                            new ExplosiveTraceDetector().checkStripe(stripe);
                            officers[1].destroyWithHighPressureWaterJet(baggageScanner.getBelt().getTracks()[0].removeFirstBaggage(), robot);
                        }

                    }
                }
            }
        }
    }

    public void shutdownScanner() {
        baggageScanner.shutdown();
    }

}
