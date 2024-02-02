package bbrz.textadventure.actions;

public interface Action {
    boolean canHandle(String command);

    void execute(String ... params);
}
