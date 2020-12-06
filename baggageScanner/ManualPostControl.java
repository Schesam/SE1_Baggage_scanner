package baggageScanner;

import rooms.IWorkingPlace;
import staff.Inspector;

public class ManualPostControl implements IWorkingPlace {
    private BaggageScanner baggageScanner;
    private Inspector inspector;

    public ManualPostControl(BaggageScanner baggageScanner, Inspector inspector) {
        this.baggageScanner = baggageScanner;
        this.inspector = inspector;
        inspector.setWorkingPlace(this);
    }

    public Inspector getInspector() {
        return inspector;
    }

    public BaggageScanner getBaggageScanner() {
        return baggageScanner;
    }
}
