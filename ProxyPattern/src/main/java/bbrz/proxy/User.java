package bbrz.proxy;

import java.util.Scanner;

public class User {
    public static void main(String[] args) {
        PinMap pinMap = new PinMap();
        String input;

        String startTxt = """
                Enter C to create an account
                Enter L to login
                Enter X to exit
                """;
        String loggedInTxt = """
                Enter B to see your account balance
                Enter D to draw money
                Enter P to Deposit money
                Enter X to logout
                """;

        while (true) {
            input = dialog(startTxt);
            if (input.equalsIgnoreCase("x")) {
                break;
            }

            else if (input.equalsIgnoreCase("c")) {
                String usr = dialog("Enter user:");
                String pin = dialog("Enter pin:");
                createAccount(usr, pin, pinMap);
            }

            else if (input.equalsIgnoreCase("l")) {
                String usr = dialog("Enter Your account name:");

                Account account = pinMap.getAccount(usr);
                Proxy proxy = new Proxy(account, pinMap);

                if (proxy.getAccount() != null) {
                    while (true) {


                        input = dialog(loggedInTxt);
                        if (input.equalsIgnoreCase("x")) {
                            proxy = null;
                            break;
                        }

                        else if (input.equalsIgnoreCase("b")) {
                            System.out.println("Your Account balance is: " + proxy.balance());
                        }

                        else if (input.equalsIgnoreCase("d")) {
                            double amount = moneyDialog("Enter the amount you want to withdraw");
                            System.out.println("You withdraw " + proxy.draw(amount) + "€");
                        }

                        else if (input.equalsIgnoreCase("p")) {
                            double amount = moneyDialog("Enter the amount you want to deposit");
                            proxy.deposit(amount);
                        } else {
                            System.out.println("Invalid Input");
                        }
                    }
                } else {
                    System.out.println("Your input was not correct");
                }
            } else {
                System.out.println("Invalid Input");
            }
        }
    }

    public static double moneyDialog(String txt) {
        String amount = dialog(txt);
        amount = amount.replace(',', '.');

        try {
            return Double.parseDouble(amount);
        } catch (NumberFormatException n) {
            System.out.println("Please enter only enter numbers");
            return 0;
        }
    }

    public static String dialog(String txt) {
        Scanner scan = new Scanner(System.in);

        System.out.println(txt);
        System.out.print(">");
        return scan.nextLine();
    }

    public static void createAccount(String usr, String pin, PinMap pinMap) {
        double initialBalance = 50.0;
        if (usr.length() > 2 && pin.length() >= 4) {
            pinMap.addPinWithAccount(new Account(initialBalance, usr), pin);
        }else {
            System.out.println("The name is to short should at least be 3 characters long or the pin is to short should at least be 4 characters long");
        }
    }
}