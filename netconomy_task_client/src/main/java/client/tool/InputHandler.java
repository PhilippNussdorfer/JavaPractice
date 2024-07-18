package client.tool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class InputHandler {
    private final Scanner scanner = new Scanner(System.in);

    public int askForPort() throws IOException {
        int portNumber = 5000;
        String input = askForInputs("Please enter the port you are connecting to when the input is invalid it wil use the standard setting 5000");
        if (stringIsNumber(input))
            portNumber = Integer.parseInt(input);
        else
            System.out.println("The input was not a number it uses now the standard 5000 port\n");

        return portNumber;
    }

    public String fileOrDirExists(String question, String errMsg) throws IOException {
        String input = "";
        boolean loop = true;

        while (loop) {
            input = askForInputs(question);
            Path path = Paths.get(input);

            if (!Files.exists(path))
                System.out.println(errMsg);
            else
                loop = false;
        }

        System.out.println("Given path: " + input + "\n");
        return input;
    }

    public String askForInputs(String question) {
        System.out.println(question);
        System.out.print("> ");
        return scanner.nextLine();
    }

    private boolean stringIsNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }
}
