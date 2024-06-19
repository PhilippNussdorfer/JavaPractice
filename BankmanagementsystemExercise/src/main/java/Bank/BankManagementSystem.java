package Bank;

import Bank.person.Admin;
import Bank.person.Person;
import Bank.person.User;
import Bank.tools.BundleFactory;
import Bank.tools.StringFormatter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Getter
public class BankManagementSystem {

    private User user;
    private Bundle bundle;
    private final Scanner scanner;
    private final StringFormatter formatter;

    public BankManagementSystem(Scanner scanner, StringFormatter formatter) {
        this.scanner = scanner;
        this.formatter = formatter;
    }

    public void lunch() {
        this.loadUsers();

        this.bundle = BundleFactory.startingBundle(this);
        this.bundle.getInterpreter().getHelpMessage();

        while(this.bundle.isRunning()) {
            this.formatter.outputWrapper("Input:");

            var input = this.getScanner().nextLine();
            this.bundle.getInterpreter().interpret(input);

            this.formatter.outputWrapper("");
        }
    }

    public void updateBundle(Bundle updatedBundle) {
        bundle = updatedBundle;
    }

    private void loadUsers() {
        List<Person> users = new ArrayList<>();
        users.add(new Admin("Hans", "2/4/1985", 123999687849L, "1122", 43L));

        user = new User(users);
    }
}
