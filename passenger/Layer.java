package passenger;

import utility.ProhibitedItem;

import java.util.Random;

public class Layer {
    private final String content;

    public Layer() {
        content = createContent();
    }

    public Layer(ProhibitedItem type) {
        content = createContent(type);
    }

    private String createContent() {
        StringBuilder s = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {
            s.append(rand.nextInt(2) == 0 ? ((char) ('a' + rand.nextInt(26))) : ((char) ('A' + rand.nextInt(26))));
        }

        return s.toString();
    }

    private String createContent(ProhibitedItem type) {
        StringBuilder s = new StringBuilder();
        Random rand = new Random();
        int pos = rand.nextInt(6000) + 1800;

        for (int i = 0; s.length() <= 10000; i++) {
            if (i == pos) {
                s.append(type.toString());
            } else {
                s.append(rand.nextInt(2) == 0 ? ((char) ('a' + rand.nextInt(26))) : ((char) ('A' + rand.nextInt(26))));
            }
        }

        return s.toString();
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Layer: " + getContent();
    }
}
