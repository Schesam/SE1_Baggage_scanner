package staff;

import authentification.CardReader;
import baggageScanner.State;
import baggageScanner.Supervision;
import utility.ProfileType;

import java.time.LocalDate;

public class Supervisor extends Employee {
    private boolean isSenior;
    private boolean isExecutive;
    private Supervision workingPlace;

    public Supervisor(String name, LocalDate birthDate, ProfileType type, int pin, LocalDate validUntil, boolean isSenior, boolean isExecutive) {
        super(name, birthDate, type, pin, validUntil);
        this.isSenior = isSenior;
        this.isExecutive = isExecutive;
    }

    public void setWorkingPlace(Supervision workingPlace) {
        this.workingPlace = workingPlace;
    }

    public void pressPowerButton() {
        State s = workingPlace.getBaggageScanner().getState();
        if (s == State.SHUTDOWN) {
            workingPlace.getBaggageScanner().start();
        } else {
            workingPlace.getBaggageScanner().shutdown();
        }
    }

    public void unlockBaggageScanner() {
        if (workingPlace.getBaggageScanner().getState() == State.LOCKED) {

        }
    }
}
