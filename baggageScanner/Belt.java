package baggageScanner;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Belt {
    private BaggageScanner baggageScanner;
    private Deque<Tray> trays = new LinkedList<>();
    private Tray trayUnderScanner;
    private Track[] tracks = new Track[2];

    public Belt(BaggageScanner baggageScanner) {
        this.baggageScanner = baggageScanner;
        tracks[0] = new Track();
        tracks[1] = new Track();
    }

    public void push(Tray t) {
        trays.add(t);
    }

    public boolean moveForward() {
        if (trayUnderScanner != null) {
            tracks[1].addTray(trayUnderScanner);
        }
        if (trays.isEmpty()) {
            return false;
        } else {
            trayUnderScanner = trays.poll();
            return true;
        }
    }

    public boolean moveToSecurityTrack() {
        if (trayUnderScanner != null) {
            tracks[0].addTray(trayUnderScanner);
            trayUnderScanner = null;
        }
        return moveForward();
    }

    public boolean moveBackward() {
        if (trayUnderScanner != null) {
            trays.addFirst(trayUnderScanner);
        }
        trayUnderScanner = tracks[0].removeTray();
        return true;
    }

    public Tray getTrayUnderScanner() {
        return trayUnderScanner;
    }

    public Track[] getTracks() {
        return tracks;
    }
}
