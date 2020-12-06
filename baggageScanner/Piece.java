package baggageScanner;

public class Piece {
    private String content;

    public Piece(String cont) {
        if (cont.length() == 50) {
            content = cont;
        } else {
            content = "";
        }
    }
}
