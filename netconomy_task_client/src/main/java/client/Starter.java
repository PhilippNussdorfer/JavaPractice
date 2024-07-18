package client;

import client.tool.InputHandler;

import java.io.IOException;

public class Starter {
    public static void main(String[] args) {
        InputHandler inputHandler = new InputHandler();
        int port = 0;
        String ServerAddress = "";
        String pathToInputFile = "";

        try {
            port = inputHandler.askForPort();
            ServerAddress = inputHandler.askForInputs("Please enter the server address");
            pathToInputFile = inputHandler.fileOrDirExists("Please enter the path to the input file",
                                                            "Could not find the file");
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }


        new Client(port, ServerAddress, pathToInputFile);
    }
}
