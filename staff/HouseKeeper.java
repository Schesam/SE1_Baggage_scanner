package staff;

import utility.ProfileType;

import java.time.LocalDate;

public class HouseKeeper extends Employee {
    public HouseKeeper(String name, LocalDate birthDate, ProfileType type, int pin, LocalDate validUntil) {
        super(name, birthDate, type, pin, validUntil);
    }
}
