package staff;

import baggageScanner.ManualPostControl;
import baggageScanner.OperatingStation;
import baggageScanner.RollerConveyor;
import baggageScanner.Tray;
import passenger.HandBaggage;
import passenger.Layer;
import rooms.CheckRoom;
import rooms.IWorkingPlace;
import utility.ProfileType;
import utility.Result;
import utility.TestStripe;

import java.time.LocalDate;

public class Inspector extends Employee {
    private boolean isSenior;
    private IWorkingPlace workingPlace;

    public Inspector(String name, LocalDate birthDate, ProfileType type, int pin, LocalDate validUntil, boolean isSenior) {
        super(name, birthDate, type, pin, validUntil);
        this.isSenior = isSenior;
    }

    public void setWorkingPlace(IWorkingPlace workingPlace) {
        this.workingPlace = workingPlace;
    }

    public boolean pushTray() {
        if (workingPlace instanceof RollerConveyor) {
            ((RollerConveyor) workingPlace).pushToBelt();
            return true;
        }
        return false;
    }

    public void pressButtonRight() {
        if (workingPlace instanceof OperatingStation) {
            ((OperatingStation) workingPlace).getBaggageScanner().moveBeltForward();
        }
    }

    public void pressButtonLeft() {
        if (workingPlace instanceof OperatingStation) {
            ((OperatingStation) workingPlace).getBaggageScanner().moveBeltBackward();
        }
    }
    public void pressAlarm() {
        if (workingPlace instanceof OperatingStation) {
            ((OperatingStation) workingPlace).getBaggageScanner().alarm();
        }
    }

    public Result pressRectangleButton() {
        if (workingPlace instanceof OperatingStation) {
            return ((OperatingStation) workingPlace).getBaggageScanner().scan();
        }
        return Result.CLEAN;
    }

    public void moveProhibitedItemToSecurityTrack() {
        if (workingPlace instanceof ManualPostControl) {
            ((ManualPostControl) workingPlace).getBaggageScanner().getBelt().moveToSecurityTrack();
        }
    }

    public void tellInspectorAboutProhibitedItem(Inspector inspector, int position) {
        if (inspector.workingPlace instanceof ManualPostControl) {
            inspector.controlBaggage(position);
        }
    }

    public void giveBaggageToOfficer(FederalPoliceOfficer officer, HandBaggage handBaggage) {
        if (workingPlace instanceof ManualPostControl) {
            officer.giveBaggage(handBaggage);
        }
    }

    public void controlBaggage(int position) {
        if (workingPlace instanceof ManualPostControl) {
            CheckRoom checkRoom = new CheckRoom();
            Tray t = ((ManualPostControl) workingPlace).getBaggageScanner().getBelt().getTracks()[0].getFirstTray();
            checkRoom.fillRoom(this, t.getHandBaggage().getOwner());
            t.getHandBaggage().getLayer()[position-1] = new Layer();
        }
    }

    public void swipe(TestStripe stripe, HandBaggage baggage) {
        if (workingPlace instanceof ManualPostControl) {
            stripe.stripeOn(baggage);
        }
    }
}
