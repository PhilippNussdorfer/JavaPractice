package Bank.person;

public class Admin extends Person {
    private Long employeeID;

    public Admin(String name, String birthDay, Long socialNumber, String pin) {
        super(name, birthDay, socialNumber, pin);
    }
}
