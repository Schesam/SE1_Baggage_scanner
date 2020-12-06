package staff;

import authentification.IDCard;

import java.time.LocalDate;

public class Employee implements IPeople {
    private static int lastId = 0;
    private int id;
    private String name;
    private LocalDate birthDate;
    private IDCard card;

    public Employee(String name, LocalDate birthDate, ProfileType type, int pin, LocalDate validUntil) {
        this.name = name;
        this.birthDate = birthDate;
        this.id = lastId++;
        this.card = new IDCard(validUntil, type, pin);
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public IDCard getCard() {
        return card;
    }


}
