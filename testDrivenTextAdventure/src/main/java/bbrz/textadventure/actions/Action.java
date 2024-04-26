package bbrz.textadventure.actions;

import bbrz.textadventure.customException.CommandNotFoundException;
import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.customException.NoItemFoundException;
import jdk.jshell.spi.ExecutionControl;

public interface Action {
    boolean canHandle(String command);

    String helpMessage();

    void execute(String ... params) throws ExecutionControl.NotImplementedException, CommandNotFoundException, NoFreeSpaceException, NoItemFoundException;

    String getName();
    String getDescription();
    String[] getPossibleCommands();
}