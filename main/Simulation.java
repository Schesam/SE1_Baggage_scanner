package main;

import baggageScanner.BaggageScanner;
import passenger.Passenger;
import rooms.CheckRoom;
import rooms.FederalPoliceOffice;
import staff.*;
import utility.*;

import java.util.Queue;

public class Simulation {
    private final Inspector[] inspectors;
    private final FederalPoliceOffice federalPoliceOffice;
    private final Supervisor supervisor;
    private final Technician technician;
    private final HouseKeeper houseKeeper;
    private final Queue<Passenger> passengers;
    private final FederalPoliceOfficer[] officers = new FederalPoliceOfficer[3];

    private final BaggageScanner baggageScanner;

    public Simulation(Inspector[] inspectors, Supervisor supervisor, FederalPoliceOffice office, Technician technician, HouseKeeper houseKeeper, Queue<Passenger> passengers) {
        this.inspectors = inspectors;
        this.federalPoliceOffice = office;
        for (int i = 0; i < officers.length; i++) {
            officers[i] = office.getOfficer(i);
        }
        this.supervisor = supervisor;
        this.technician = technician;
        this.houseKeeper = houseKeeper;

        this.baggageScanner = new BaggageScanner(technician, inspectors[0], inspectors[1], inspectors[2], supervisor);
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
            Passenger currentPassenger = passengers.poll();
            while (currentPassenger.hasBaggage()) {
                baggageScanner.getRoller().fillTray(currentPassenger.getNextBaggage());
            }
            while (!baggageScanner.getRoller().getTrays().isEmpty()) {
                inspectors[0].pushTray();
                inspectors[1].pressButtonRight();
                Result res = inspectors[1].pressRectangleButton();
                if (res == Result.CLEAN && !federalPoliceOffice.isArrested(currentPassenger)) {
                    currentPassenger.addBaggage(baggageScanner.getBelt().getTracks()[1].removeFirstBaggage());
                }
                while (res != Result.CLEAN) {
                    if (res == Result.KNIFE) {
                        System.out.println("Messer gefunden: " + currentPassenger);
                        baggageScanner.report(res.getPosition());
                        inspectors[1].pressButtonLeft();
                        inspectors[1].pressRectangleButton();
                    } else if (res == Result.EXPLOSIVE || res == Result.WEAPON) {
                        executeDangerousProhibitedFund(currentPassenger, res);
                    }
                    res = Result.CLEAN;
                    supervisor.unlockBaggageScanner();
                }
            }
        }
        shutdownScanner();
    }

    private void executeDangerousProhibitedFund(Passenger currentPassenger, Result res) {
        inspectors[1].pressAlarm();
        System.out.println("Alarm ausgelöst: " + currentPassenger);
        Passenger p = baggageScanner.getBelt().getTracks()[0].getFirstTray().getHandBaggage().getOwner();
        federalPoliceOffice.getOfficer(0).arrest(p);
        if (res == Result.WEAPON) {
            executeWeaponFund(currentPassenger, res);
        } else {
            executeExplosionFund(currentPassenger);
        }
    }

    private void executeWeaponFund(Passenger currentPassenger, Result res) {
        System.out.println("Waffe gefunden: " + currentPassenger);
        CheckRoom<IPeople> room = new CheckRoom<>();
        room.fillRoom(officers[0], officers[1], officers[2], supervisor);
        officers[0].controlBaggage(res.getPosition(), baggageScanner.getBelt().getTracks()[0].getFirstTray());
        officers[0].giveWeapon(officers[2], ProhibitedItem.GLOCK7);
        while (!baggageScanner.getRoller().getTrays().isEmpty()) {
            supervisor.unlockBaggageScanner();
            inspectors[0].pushTray();
            inspectors[1].pressButtonRight();
            res = inspectors[1].pressRectangleButton();
            if (res.isProhibited()) {
                if (res == Result.WEAPON) {
                    executeWeaponFund(currentPassenger, res);
                    inspectors[2].giveBaggageToOfficer(officers[2], baggageScanner.getBelt().getTracks()[0].removeFirstBaggage());
                } else {
                    executeExplosionFund(currentPassenger);
                }
            }
        }
        officers[1].leave();
        officers[2].leave();
        room.clearRoom();
    }

    private void executeExplosionFund(Passenger currentPassenger) {
        System.out.println("Sprengstoff gefunden: " + currentPassenger);
        Robot robot = federalPoliceOffice.getRobot();
        TestStripe stripe = new TestStripe();
        inspectors[2].swipe(stripe, baggageScanner.getBelt().getTracks()[0].getFirstTray().getHandBaggage());
        new ExplosiveTraceDetector().checkStripe(stripe);
        officers[1].destroyWithHighPressureWaterJet(baggageScanner.getBelt().getTracks()[0].removeFirstBaggage(), robot);
    }

    public void shutdownScanner() {
        baggageScanner.shutdown();
    }

}
