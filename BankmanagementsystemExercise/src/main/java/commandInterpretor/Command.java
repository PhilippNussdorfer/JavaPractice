package commandInterpretor;

public interface Command {
    boolean canHandle(String command);
    void execute(String[] params);
    String help();
}