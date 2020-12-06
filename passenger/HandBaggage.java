package passenger;

import utility.ProhibitedItem;

public class HandBaggage {
    private Layer[] layer = new Layer[5];
    private Passenger owner = null;

    public HandBaggage(ProhibitedItem item, int pos) {
        this();
        layer[pos - 1] = new Layer(item);
    }

    public HandBaggage() {
        for (int i = 0; i < layer.length; i++) {
            layer[i] = new Layer();
        }
    }

    public Layer[] getLayer() {
        return layer;
    }

    public Passenger getOwner() {
        return owner;
    }

    public void setOwner(Passenger owner) {
        this.owner = owner;
    }
}
