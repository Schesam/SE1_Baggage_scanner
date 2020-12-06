package rooms;

import passenger.Passenger;
import staff.FederalPoliceOfficer;
import staff.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FederalPoliceOffice {
    private List<FederalPoliceOfficer> officers = new ArrayList<>();
    private List<Passenger> arrested = new ArrayList<>();
    private Robot[] robots = {new Robot(), new Robot(), new Robot()};

    public void addOfficer(FederalPoliceOfficer officer) {
        officers.add(officer);
    }

    public FederalPoliceOfficer getOfficer(int num) {
        return officers.get(num);
    }

    public void addArrestedPassenger(Passenger p) {
        arrested.add(p);
    }

    public Robot getRobot() {
        return robots[new Random().nextInt(robots.length)];
    }
}
