package Bank.person;

import lombok.Getter;

public class Admin extends Person {
    @Getter
    private final Long employeeID;

    public Admin(String name, String birthDay, Long socialNumber, String pin, Long employeeID) {
        super(name, birthDay, socialNumber, pin);

        this.employeeID = employeeID;
    }
}
