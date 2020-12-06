package baggageScanner;

import baggageScanner.TestStripe;
import library.text_search.BruteForce;

import java.util.Arrays;

public class ExplosiveTraceDetector {
    public boolean checkStripe(TestStripe stripe) {
        return Arrays.stream(stripe.getContent()).map(String::new).filter(c -> new BruteForce().search(c, "exp") != -1).count() > 0;
    }
}
