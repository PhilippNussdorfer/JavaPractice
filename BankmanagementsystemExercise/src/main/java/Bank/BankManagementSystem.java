package Bank;

import Bank.person.Admin;
import Bank.person.Person;
import Bank.person.User;
import Bank.tools.BundleFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankManagementSystem {
    private final List<Person> users = new ArrayList<>();
    private User user;
    private static Bundle bundle;

    public static void main(String[] args) {
        BankManagementSystem system = new BankManagementSystem();
        system.loadUsers(system);

        Scanner scanner = new Scanner(System.in);
        bundle = BundleFactory.startingBundle(system.user);
        bundle.getInterpreter().getHelpMessage();

        while(bundle.isRunning) {
            System.out.print("> ");

            var input = scanner.nextLine();
            bundle.getInterpreter().interpret(input);

            System.out.println();
        }
    }

    public static void updateBundle(Bundle updatedBundle) {
        bundle = updatedBundle;
    }

    private void loadUsers(BankManagementSystem system) {
        system.users.add(new Admin("Hans", "2/4/1985", 123999687849L, "1122", 43L));

        system.user = new User(users);
    }
}
