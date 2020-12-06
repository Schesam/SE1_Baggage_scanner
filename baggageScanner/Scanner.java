package baggageScanner;

import passenger.Layer;
import main.Configuration;

public class Scanner {

    private int scanCount = 0;

    public Record scan(Tray t) {
        scanCount++;
        Layer[] layer = t.getHandBaggage().getLayer();
        for (int i = 0; i < layer.length; i++) {
            if (Configuration.getCurrentAlg().search(layer[i].getContent(), ProhibitedItem.EXPLOSIVE.toString()) != -1) {
                return new Record(Result.EXPLOSIVE, i + 1);
            } else if (Configuration.getCurrentAlg().search(layer[i].getContent(), ProhibitedItem.KNIFE.toString()) != -1) {
                return new Record(Result.KNIFE, i + 1);
            } else if (Configuration.getCurrentAlg().search(layer[i].getContent(), ProhibitedItem.GLOCK7.toString()) != -1) {
                return new Record(Result.WEAPON, i + 1);
            }
        }
        return new Record(Result.CLEAN, -1);
    }

    public int getScanCount() {
        return scanCount;
    }
}
