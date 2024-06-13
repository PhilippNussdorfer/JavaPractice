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

    public static void main(String[] args) {
        BankManagementSystem system = new BankManagementSystem();
        system.loadUsers();

        Scanner scanner = new Scanner(System.in);
        system.bundle = BundleFactory.startingBundle(system);
        system.bundle.getInterpreter().getHelpMessage();

        while(system.bundle.isRunning) {
            System.out.print("> ");

            var input = scanner.nextLine();
            system.bundle.getInterpreter().interpret(input);

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
