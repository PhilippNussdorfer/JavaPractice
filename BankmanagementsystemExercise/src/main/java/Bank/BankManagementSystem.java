package Bank;

import Bank.person.Admin;
import Bank.person.Person;
import Bank.person.User;
import Bank.tools.BundleFactory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankManagementSystem {
    @Getter
    private User user;
    private Bundle bundle;
    @Getter
    private final Scanner scanner;

    public BankManagementSystem(Scanner scanner) {
        this.scanner = scanner;
    }

    public void lunch() {
        this.loadUsers();

        this.bundle = BundleFactory.startingBundle(this);
        this.bundle.getInterpreter().getHelpMessage();

        while(this.bundle.isRunning()) {
            System.out.print("> ");

            var input = this.getScanner().nextLine();
            this.bundle.getInterpreter().interpret(input);

            System.out.println();
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
