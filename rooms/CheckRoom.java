package rooms;

import staff.IPeople;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckRoom<T extends IPeople> {
    private List<T> people = new ArrayList<>();

    public void fillRoom(T... people) {
        this.people.addAll(Arrays.asList(people));
    }

    public void clearRoom() {
        people.clear();
    }
}
