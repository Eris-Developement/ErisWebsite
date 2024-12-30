package fr.eris.exception.command;

import fr.eris.command.ICommand;

public class RegisteringSimilarCommandException extends RuntimeException
{
    public RegisteringSimilarCommandException(ICommand command, ICommand otherCommand) {
        super(String.format("Attempting to register similar command. {%s}|{%s}",
                command.getClass().getName(),
                otherCommand.getClass().getName()));
    }
}
