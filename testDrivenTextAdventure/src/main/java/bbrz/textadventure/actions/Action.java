package bbrz.textadventure.actions;

import bbrz.textadventure.customException.CommandNotFoundException;
import jdk.jshell.spi.ExecutionControl;

public interface Action {
    boolean canHandle(String command);

    void execute(String ... params) throws ExecutionControl.NotImplementedException, CommandNotFoundException;
}
