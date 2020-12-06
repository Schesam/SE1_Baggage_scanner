package staff;

import passenger.HandBaggage;
import baggageScanner.Piece;

public class Remote {
    private Robot robot;

    public Remote(Robot robot) {
        this.robot = robot;
    }

    public Piece[] destroyWithHighPressureWaterJet(HandBaggage baggage) {
        return robot.destroyWithHighPressureWaterJet(baggage);
    }
}
