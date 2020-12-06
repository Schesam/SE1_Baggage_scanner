package utility;

import java.util.Arrays;
import java.util.Random;

public class TestStripe {
    private final int width = 10;
    private final int length = 30;
    private char[][] content = new char[length][width];

    public TestStripe() {
        for (char[] cont:content) {
            Arrays.fill(cont, 'p');
        }
    }

    public void placeExp(){
        Random r = new Random();
        int i = r.nextInt(length);
        int j = r.nextInt(width-3);

        content[i][j] = 'e';
        content[i][j+1] = 'x';
    }

    public char[][] getContent() {
        return content;
    }
}
