package passenger;

import baggageScanner.ProhibitedItem;

import java.util.Arrays;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HandBaggage that = (HandBaggage) o;
        return Arrays.equals(layer, that.layer) && Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(owner);
        result = 31 * result + Arrays.hashCode(layer);
        return result;
    }
}
