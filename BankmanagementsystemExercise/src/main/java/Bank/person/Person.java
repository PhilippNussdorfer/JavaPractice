package Bank.person;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class Person {
    @Getter
    private String name;
    @Getter
    private String birthDay;
    @Getter
    private Long socialNumber;
    private String pin;

    public boolean login(String pin) {
        return pin.equals(this.pin);
    }
}
