package main;

import baggageScanner.*;
import passenger.Passenger;
import rooms.CheckRoom;
import rooms.FederalPoliceOffice;
import staff.*;

import java.util.LinkedList;
import java.util.Queue;

public class Simulation {
    private Inspector[] inspectors;
    private final FederalPoliceOffice federalPoliceOffice;
    private final Supervision supervision;
    private final Technician technician;
    private final HouseKeeper houseKeeper;
    private final Queue<Passenger> passengers;
    private final Queue<ProgramState> logStates = new LinkedList<>();

    private final BaggageScanner baggageScanner;

    public Simulation(Inspector[] inspectors, Supervisor supervisor, FederalPoliceOffice office, Technician technician, HouseKeeper houseKeeper, Queue<Passenger> passengers) {
        this.inspectors = inspectors;
        this.federalPoliceOffice = office;
        this.technician = technician;
        this.houseKeeper = houseKeeper;

        this.baggageScanner = new BaggageScanner(technician, inspectors[0], inspectors[1], inspectors[2], supervisor);
        this.supervision = new Supervision(baggageScanner,supervisor);
        this.passengers = passengers;
    }

    public void startScanner() {
        logStates.add(ProgramState.SCANNER_START);
        baggageScanner.getSupervision().getSupervisor().pressPowerButton();
    }

    public void activateScanner() {
        logStates.add(ProgramState.SCANNER_ACTIVATE);
        baggageScanner.getOpStation().activateBaggageScanner(inspectors[1].getBirthDate().getYear());
    }

    public void run() {
        startScanner();
        activateScanner();

        while (!passengers.isEmpty()){
            logStates.add(ProgramState.LOAD_NEXT_PASSENGER);
            Passenger currentPassenger = passengers.poll();
            while (currentPassenger.hasBaggage()) {
                baggageScanner.getRoller().fillTray(currentPassenger.getNextBaggage());
            }
            logStates.add(ProgramState.BAGGAGE_ON_ROLLER);
            while (!baggageScanner.getRoller().getTrays().isEmpty()) {
                inspectors[0].pushTray();
                inspectors[1].pressButtonRight();
                Result res = inspectors[1].pressRectangleButton();
                logStates.add(ProgramState.TRAY_SCANNED);
                if (res == Result.CLEAN && !federalPoliceOffice.isArrested(currentPassenger)) {
                    currentPassenger.addBaggage(baggageScanner.getBelt().getTracks()[1].removeFirstBaggage());
                    logStates.add(ProgramState.BAGGAGE_TAKEN);
                }
                while (res != Result.CLEAN) {
                    if (res == Result.KNIFE) {
                        logStates.add(ProgramState.KNIFE_FOUND);
                        //System.out.println("Messer gefunden: " + currentPassenger);
                        baggageScanner.report(res.getPosition());
                        inspectors[1].pressButtonLeft();
                        Result r = inspectors[1].pressRectangleButton();
                        inspectors[1].pressButtonRight();
                        logStates.add(ProgramState.AFTER_KNIFE_SCANNED);
                        if (r == Result.CLEAN) {
                            currentPassenger.addBaggage(baggageScanner.getBelt().getTracks()[1].removeFirstBaggage());
                        }
                    } else if (res == Result.EXPLOSIVE || res == Result.WEAPON) {
                        executeDangerousProhibitedFund(currentPassenger, res);
                    }
                    res = Result.CLEAN;
                    supervision.getSupervisor().unlockBaggageScanner();
                }
            }
        }
        shutdownScanner();
    }

    private void executeDangerousProhibitedFund(Passenger currentPassenger, Result res) {
        inspectors[1].pressAlarm();
        logStates.add(ProgramState.ALARM_CREATED);
        //System.out.println("Alarm ausgelöst: " + currentPassenger);
        Passenger p = baggageScanner.getBelt().getTracks()[0].getFirstTray().getHandBaggage().getOwner();
        federalPoliceOffice.getOfficer(0).arrest(p);
        logStates.add(ProgramState.PASSENGER_ARRESTED);
        if (res == Result.WEAPON) {
            logStates.add(ProgramState.WEAPON_FOUND);
            executeWeaponFund(currentPassenger, res);
        } else {
            logStates.add(ProgramState.EXPLOSIVE_FOUND);
            executeExplosionFund(currentPassenger);
        }
    }

    private void executeWeaponFund(Passenger currentPassenger, Result res) {
        //System.out.println("Waffe gefunden: " + currentPassenger);
        CheckRoom<IPeople> room = new CheckRoom<>();
        room.fillRoom(federalPoliceOffice.getOfficer(0), federalPoliceOffice.getOfficer(1), federalPoliceOffice.getOfficer(2), supervision.getSupervisor());
        federalPoliceOffice.getOfficer(0).controlBaggage(res.getPosition(), baggageScanner.getBelt().getTracks()[0].getFirstTray());
        federalPoliceOffice.getOfficer(0).giveWeapon(federalPoliceOffice.getOfficer(2), ProhibitedItem.GLOCK7);
        logStates.add(ProgramState.WEAPON_TAKEN);
        while (!baggageScanner.getRoller().getTrays().isEmpty()) {
            supervision.getSupervisor().unlockBaggageScanner();
            inspectors[0].pushTray();
            inspectors[1].pressButtonRight();
            res = inspectors[1].pressRectangleButton();
            if (res.isProhibited()) {
                if (res == Result.WEAPON) {
                    executeWeaponFund(currentPassenger, res);
                    inspectors[2].giveBaggageToOfficer(federalPoliceOffice.getOfficer(2), baggageScanner.getBelt().getTracks()[0].removeFirstBaggage());
                } else {
                    executeExplosionFund(currentPassenger);
                }
            }
        }
        federalPoliceOffice.getOfficer(1).leave();
        federalPoliceOffice.getOfficer(2).leave();
        room.clearRoom();
    }

    private void executeExplosionFund(Passenger currentPassenger) {
        //System.out.println("Sprengstoff gefunden: " + currentPassenger);
        Robot robot = federalPoliceOffice.getRobot();
        TestStripe stripe = new TestStripe();
        inspectors[2].swipe(stripe, baggageScanner.getBelt().getTracks()[0].getFirstTray().getHandBaggage());
        new ExplosiveTraceDetector().checkStripe(stripe);
        logStates.add(ProgramState.SWIPED);
        federalPoliceOffice.getOfficer(1).destroyWithHighPressureWaterJet(baggageScanner.getBelt().getTracks()[0].removeFirstBaggage(), robot);
        logStates.add(ProgramState.EXPLOSIVE_DESTROYED);
    }

    public void shutdownScanner() {
        logStates.add(ProgramState.SCANNER_SHUTDOWN);
        baggageScanner.shutdown();
    }

    public Queue<Passenger> getPassengers() {
        return passengers;
    }

    public Queue<ProgramState> getLogStates() {
        return logStates;
    }

    public BaggageScanner getBaggageScanner() {
        return baggageScanner;
    }

    public Inspector[] getInspectors() {
        return inspectors;
    }
    public void setInspectors(Inspector[] insp) {
        inspectors = insp;
    }
    public FederalPoliceOffice getFederalPoliceOffice() {
        return federalPoliceOffice;
    }

    public Supervision getSupervision() {
        return supervision;
    }

    public Technician getTechnician() {
        return technician;
    }

    public HouseKeeper getHouseKeeper() {
        return houseKeeper;
    }
}
