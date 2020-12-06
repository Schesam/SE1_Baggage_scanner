package staff;

import passenger.HandBaggage;
import passenger.Layer;
import baggageScanner.Piece;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Robot {

    public Piece[] destroyWithHighPressureWaterJet(HandBaggage baggage) {
        Piece[] pieces = new Piece[1000];
        String content = Arrays.stream(baggage.getLayer()).map(Layer::getContent).collect(Collectors.joining(""));

        for(int i=0; i<1000; i++){
            pieces[i] = new Piece(content.substring(i*50, (i+1)*50));
        }

        return pieces;
    }
}
