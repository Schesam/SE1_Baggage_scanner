package baggageScanner;

import passenger.Passenger;
import rooms.FederalPoliceOffice;
import staff.Inspector;
import staff.Supervisor;
import staff.Technician;
import utility.Result;

import java.util.LinkedList;
import java.util.Queue;

public class BaggageScanner {
    private final Queue<Tray> trays;
    private final RollerConveyor roller;
    private final Belt belt;
    private final Scanner scanner;
    private final OperatingStation opStation;
    private final ManualPostControl manualPostControl;
    private final Supervision supervision;
    private State state = State.SHUTDOWN;
    private final Algorithm alg;
    private final Technician technician;
    private final Inspector[] inspectors = new Inspector[3];
    private FederalPoliceOffice office = new FederalPoliceOffice();

    public BaggageScanner(Algorithm alg, Technician technician, Inspector i1, Inspector i2, Inspector i3, Supervisor sup, FederalPoliceOffice office) {
        this.alg = alg;
        this.technician = technician;
        inspectors[0] = i1;
        inspectors[1] = i2;
        inspectors[2] = i3;
        this.supervision = new Supervision(this, sup);
        sup.setWorkingPlace(supervision);
        this.roller = new RollerConveyor(this, i1);
        this.opStation = new OperatingStation(this, i2);
        this.manualPostControl = new ManualPostControl(this, i3);
        this.office = office;
        this.belt = new Belt(this);
        this.scanner = new Scanner();
        this.trays = new LinkedList<>();
    }

    public State getState() {
        return state;
    }

    public Supervision getSupervision() {
        return supervision;
    }

    public RollerConveyor getRoller() {
        return roller;
    }

    public OperatingStation getOpStation() {
        return opStation;
    }

    public ManualPostControl getManualPostControl() {
        return manualPostControl;
    }

    private boolean checkState() {
        return state != State.LOCKED && state != State.SHUTDOWN && state != State.DEACTIVATED && state != State.INUSE;
    }

    public void moveBeltForward() {
        if (checkState()) {
            belt.moveForward();
        }
    }

    public void moveBeltBackward() {
        if (checkState()) {
            belt.moveBackward();
        }
    }

    public Belt getBelt() {
        return belt;
    }

    public Result scan() {
        if (checkState()) {
            state = State.INUSE;
            Result res = scanner.scan(belt.getTrayUnderScanner()).getResult();
            if (res.isProhibited()) {
                inspectors[2].moveProhibitedItemToSecurityTrack();
            }
            state = State.ACTIVATED;
            return res;
        }
        return Result.CLEAN;
    }

    public void alarm() {
        if (checkState()) {
            state = State.LOCKED;
        }
    }

    public void report(int position) {
        if (checkState()) {
            inspectors[1].tellInspectorAboutProhibitedItem(inspectors[2], position);
        }
    }

    public void maintenance() {
        if (checkState()) {
            technician.maintain(this);
        }
    }

    public void start() {
        if (state == State.SHUTDOWN) {
            state = State.DEACTIVATED;
        }
    }

    void activate() {
        state = State.ACTIVATED;
    }

    public void shutdown() {
        maintenance();
        state = State.SHUTDOWN;
    }
}