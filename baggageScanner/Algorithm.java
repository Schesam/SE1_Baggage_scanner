package baggageScanner;

import library.text_search.BoyerMoore;
import library.text_search.IStringMatching;
import library.text_search.KnuthMorrisPratt;

public enum Algorithm implements IStringMatching {
    BOYERMOORE(new BoyerMoore()), KNUTHMORRISPRATT(new KnuthMorrisPratt());


    Algorithm(IStringMatching matcher) {
        this.matcher = matcher;
    }
    private final IStringMatching matcher;

    @Override
    public int search(String text, String pattern) {
        return matcher.search(text, pattern);
    }
}
