package baggageScanner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Record {
    private static int lastId = 0;
    private int id;
    private String timestamp;
    private Result result = Result.CLEAN;

    public Record(Result result, int layer) {
        timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss,SSS"));
        this.id = lastId++;
        this.result = result;
        result.setPosition(layer);
    }

    public Result getResult() {
        return result;
    }
}
