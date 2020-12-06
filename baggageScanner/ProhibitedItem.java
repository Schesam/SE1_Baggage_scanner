package baggageScanner;

public enum ProhibitedItem {
    KNIFE("kn!fe"), GLOCK7("glock|7"), EXPLOSIVE("exp|os!ve");

    private ProhibitedItem(String intern) {
        this.intern = intern;
    }

    private String intern;

    @Override
    public String toString() {
        return intern;
    }
}
