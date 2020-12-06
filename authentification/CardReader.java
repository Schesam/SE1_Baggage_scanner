package authentification;

import baggageScanner.BaggageScanner;
import baggageScanner.State;
import staff.ProfileType;

public class CardReader {

    public boolean unlockOperatingStationValid(IDCard card, int enteredPin, BaggageScanner baggageScanner){
        return  (baggageScanner.getState() == State.LOCKED && card.getMagnetStripe().getProfileType() == ProfileType.S
                || card.getMagnetStripe().getProfileType() != ProfileType.K && card.getMagnetStripe().getProfileType() != ProfileType.O)
                && card.getMagnetStripe().checkPIN(enteredPin);
    }
}
