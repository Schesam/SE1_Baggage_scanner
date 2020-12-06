package baggageScanner;

import passenger.HandBaggage;
import rooms.IWorkingPlace;
import staff.Inspector;

import java.util.LinkedList;
import java.util.Queue;

public class RollerConveyor implements IWorkingPlace {
    private BaggageScanner baggageScanner;
    private Inspector inspector;
    private Queue<Tray> trays;
    private Queue<Tray> filledTrays;

    public RollerConveyor(BaggageScanner baggageScanner, Inspector inspector) {
        this.baggageScanner = baggageScanner;
        this.inspector = inspector;
        inspector.setWorkingPlace(this);
        trays = new LinkedList<>();
        filledTrays = new LinkedList<>();
        for (int i = 0; i < 1000; i++) {
            trays.add(new Tray());
        }
    }

    public Inspector getInspector() {
        return inspector;
    }

    public Queue<Tray> getTrays() {
        return filledTrays;
    }

    public boolean fillTray(HandBaggage handBaggage) {
        if (trays.isEmpty()) {
            return false;
        } else {
            Tray t = trays.poll();
            t.fill(handBaggage);
            filledTrays.add(t);
            return true;
        }
    }

    public boolean pushToBelt() {
        if (!filledTrays.isEmpty()) {
            baggageScanner.getBelt().push(filledTrays.poll());
            return true;
        }
        return false;
    }

}
