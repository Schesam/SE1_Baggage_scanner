package baggageScanner;

import passenger.Layer;
import utility.Configuration;
import utility.ProhibitedItem;
import utility.Result;
import utility.Record;

public class Scanner {


    public Record scan(Tray t) {
        Layer[] layer = t.getHandBaggage().getLayer();
        for (int i=0; i<layer.length; i++) {
            if(Configuration.getCurrentAlg().search(layer[i].getContent(), ProhibitedItem.EXPLOSIVE.toString()) != -1) {
                return new Record(Result.EXPLOSIVE, i+1);
            }else if(Configuration.getCurrentAlg().search(layer[i].getContent(), ProhibitedItem.KNIFE.toString()) != -1){
                return new Record(Result.KNIFE, i+1);
            }else if(Configuration.getCurrentAlg().search(layer[i].getContent(), ProhibitedItem.GLOCK7.toString()) != -1){
                return new Record(Result.WEAPON, i+1);
            }
        }
        return new Record(Result.CLEAN, -1);
    }
}
