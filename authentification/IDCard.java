package authentification;

import utility.ProfileType;

import java.time.LocalDate;

public class IDCard {
    private static int lastId = 0;
    private final int id;
    private final MagnetStripe magnetStripe;
    private final LocalDate validUntil;
    private boolean isLocked = false;
    private int numWrongEntrys = 0;
    private final CardType type;

    public IDCard(LocalDate validUntil, ProfileType type, int pin) {
        this.id = lastId++;
        this.magnetStripe = new MagnetStripe(type, pin);
        if (type == ProfileType.I || type == ProfileType.S) {
            this.type = CardType.STAFF;
        } else {
            this.type = CardType.EXTERNAL;
        }
        this.validUntil = validUntil;
    }

    public MagnetStripe getMagnetStripe() {
        return magnetStripe;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public boolean isValid() {
        return isLocked() && !LocalDate.now().until(getValidUntil()).isNegative();
    }

    public CardType getType() {
        return type;
    }

    public void addWrongPinEntry() {
        numWrongEntrys++;
        isLocked = numWrongEntrys >= 3;
    }

    public void unlock() {
        numWrongEntrys = 0;
        isLocked = false;
    }

}
