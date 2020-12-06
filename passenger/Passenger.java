package passenger;

import utility.IPeople;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Passenger implements IPeople {
    private Queue<HandBaggage> bags;
    private String name;

    public Passenger(String name, HandBaggage... h){
        this.name = name;
        bags = new LinkedList<>();
        Arrays.stream(h).limit(3).forEach(bags::add);
        Arrays.stream(h).limit(3).forEach(hb -> hb.setOwner(this));
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
}
