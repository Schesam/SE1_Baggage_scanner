package baggageScanner;

import rooms.IWorkingPlace;
import staff.Supervisor;

public class Supervision  implements IWorkingPlace {
    private Supervisor supervisor;
    private BaggageScanner baggageScanner;

    public Supervision(BaggageScanner bg, Supervisor visor) {
        supervisor = visor;
        baggageScanner = bg;
        visor.setWorkingPlace(this);
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public BaggageScanner getBaggageScanner() {
        return baggageScanner;
    }
}
