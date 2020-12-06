package baggageScanner;

import passenger.HandBaggage;

public class Tray {
    private HandBaggage handBaggage;

    public HandBaggage getHandBaggage() {
        return handBaggage;
    }

    public void fill(HandBaggage handBaggage) {
        if (handBaggage != null) {
            this.handBaggage = handBaggage;
        }
    }

    public HandBaggage remove() {
        HandBaggage b = handBaggage;
        handBaggage = null;
        return b;
    }
}
