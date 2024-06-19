import Bank.BankManagementSystem;
import Bank.tools.StringFormatter;

import java.util.Scanner;

public class Starter {
    public static void main(String[] args) {
        BankManagementSystem system = new BankManagementSystem(new Scanner(System.in), new StringFormatter());

        system.lunch();
    }
}
