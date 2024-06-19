import Bank.BankManagementSystem;

import java.util.Scanner;

public class Starter {
    public static void main(String[] args) {
        BankManagementSystem system = new BankManagementSystem(new Scanner(System.in));

        system.lunch();
    }
}
