package baggageScanner;

import passenger.HandBaggage;

import java.util.LinkedList;
import java.util.Queue;

public class Track {
    private Queue<Tray> trays;

    public Track(){
        trays = new LinkedList<>();
    }

    public HandBaggage removeFirstBaggage(){
        if (trays.isEmpty()) {
            return null;
        }
        return trays.poll().getHandBaggage();
    }

    public Tray removeTray(){
        if (trays.isEmpty()) {
            return null;
        }
        return trays.poll();
    }

    public Tray getFirstTray(){
        if (trays.isEmpty()) {
            return null;
        }
        return trays.peek();
    }

    protected void addTray(Tray t){
        trays.add(t);
    }
}
