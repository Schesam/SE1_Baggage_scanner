package baggageScanner;

import authentification.CardReader;
import rooms.IWorkingPlace;
import staff.Inspector;
import staff.Supervisor;

public class OperatingStation implements IWorkingPlace {
    private BaggageScanner baggageScanner;
    private Inspector inspector;
    private CardReader reader;

    public OperatingStation(BaggageScanner baggageScanner, Inspector inspector) {
        this.baggageScanner = baggageScanner;
        this.inspector = inspector;
        inspector.setWorkingPlace(this);
        this.reader = new CardReader();
    }

    public Inspector getInspector() {
        return inspector;
    }

    public BaggageScanner getBaggageScanner() {
        return baggageScanner;
    }

    public boolean activateBaggageScanner(int pin) {
        if(inspector.getCard().isValid() && reader.unlockOperatingStationValid(inspector.getCard(), pin, baggageScanner)) {
            baggageScanner.activate();
            inspector.getCard().unlock();
            return true;
        }
        inspector.getCard().addWrongPinEntry();
        return false;
    }

    public boolean unlockBaggageScanner(Supervisor supervisor, int pin) {
        if(supervisor.getCard().isValid() && reader.unlockOperatingStationValid(supervisor.getCard(), pin, baggageScanner)) {
            baggageScanner.unlock();
            inspector.getCard().unlock();
            return true;
        }
        inspector.getCard().addWrongPinEntry();
        return false;
    }

}
