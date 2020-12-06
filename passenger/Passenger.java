package passenger;

import staff.IPeople;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class Passenger implements IPeople {
    private final Queue<HandBaggage> bags;
    private final String name;

    public Passenger(String name, HandBaggage... h) {
        this.name = name;
        bags = new LinkedList<>();
        Arrays.stream(h).limit(3).forEach(hb -> {
            hb.setOwner(this);
            bags.add(hb);
        });
    }

    public Queue<HandBaggage> getBags() {
        return bags;
    }

    public boolean hasBaggage() {
        return !bags.isEmpty();
    }

    public HandBaggage getNextBaggage() {
        return bags.poll();
    }

    public void addBaggage(HandBaggage handBaggage) {
        bags.add(handBaggage);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Passenger passenger = (Passenger) o;
        return Objects.equals(bags, passenger.bags) && Objects.equals(name, passenger.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bags, name);
    }
}
