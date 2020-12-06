package staff;

import baggageScanner.ManualPostControl;
import baggageScanner.Tray;
import passenger.HandBaggage;
import passenger.Layer;
import passenger.Passenger;
import rooms.CheckRoom;
import rooms.FederalPoliceOffice;
import utility.ProfileType;
import utility.ProhibitedItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FederalPoliceOfficer extends Employee {
    private String grade;
    private FederalPoliceOffice office;
    private Passenger arrestedPassenger;
    private List<ProhibitedItem> takenWeapons = new ArrayList<>();
    private List<HandBaggage> takenBaggage = new ArrayList<>();


    public FederalPoliceOfficer(String name, LocalDate birthDate, ProfileType type, int pin, LocalDate validUntil, String grade, FederalPoliceOffice office) {
        super(name, birthDate, type, pin, validUntil);
        this.grade = grade;
        this.office = office;
        office.addOfficer(this);
    }

    public void arrest(Passenger p) {
        office.addArrestedPassenger(p);
        arrestedPassenger = p;
    }

    public void giveWeapon(FederalPoliceOfficer officer, ProhibitedItem item) {
        officer.takenWeapons.add(item);
    }

    public void giveBaggage(HandBaggage item) {
        takenBaggage.add(item);
    }

    public void controlBaggage(int position, Tray t) {
            t.getHandBaggage().getLayer()[position-1] = new Layer();
    }

    public void leave() {
        arrestedPassenger = null;
        takenWeapons.clear();
        takenBaggage.clear();
    }
}
