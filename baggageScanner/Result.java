package baggageScanner;

public enum Result {
    CLEAN, KNIFE, WEAPON, EXPLOSIVE;

    private int position = -1;

    private String getOutputName() {
        return switch(this) {
            case KNIFE, EXPLOSIVE, CLEAN -> this.name().toLowerCase();
            case WEAPON -> this.name().toLowerCase() + " - glock7";
        };
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public boolean isProhibited() {
        return this != CLEAN;
    }

    @Override
    public String toString() {
        return this == CLEAN ? getOutputName() :"prohibited item | " + getOutputName() + " detected at position " + position;
    }
}
