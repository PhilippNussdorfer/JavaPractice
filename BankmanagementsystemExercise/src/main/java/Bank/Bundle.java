package Bank;

import Bank.commandInterpretor.Interpreter;
import Bank.person.Admin;
import Bank.person.Customer;
import Bank.person.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Bundle {
    private Interpreter interpreter = null;
    private final List<Customer> customers = new ArrayList<>();
    private final List<Admin> admins = new ArrayList<>();
    @Setter
    private Person user;

    public void addInterpreter(Interpreter interpreter) {
        this.interpreter = interpreter;
    }
}
