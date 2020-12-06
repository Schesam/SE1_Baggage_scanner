package staff;

import baggageScanner.BaggageScanner;
import utility.ProfileType;

import java.time.LocalDate;

public class Technician extends Employee {
    public Technician(String name, LocalDate birthDate, ProfileType type, int pin, LocalDate validUntil) {
        super(name, birthDate, type, pin, validUntil);
    }

    public void maintain(BaggageScanner scanner) {

    }
}
