package Bank.person;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Person {
    private String name;
    private String birthDay;
    private Long socialNumber;
    private String pin;

    public boolean login(String pin) {
        return pin.equals(this.pin);
    }
}
