package main;

import baggageScanner.Algorithm;

public class Configuration {

    private static Algorithm currentAlg = Algorithm.BOYERMOORE;
    public static final String DATA_PATH = "passenger_baggage.txt";

    public static Algorithm getCurrentAlg() {
        return currentAlg;
    }

    public static void setCurrentAlg(Algorithm currentAlg) {
        Configuration.currentAlg = currentAlg;
    }
}
