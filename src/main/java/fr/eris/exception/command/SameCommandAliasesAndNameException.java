package fr.eris.exception.command;

import fr.eris.command.ICommand;

public class SameCommandAliasesAndNameException extends RuntimeException
{
    public SameCommandAliasesAndNameException(ICommand command) {
        super(String.format("Attempting to create command with same name and aliases. {name:'%s'}|{aliases:'%s'}",
                command.getName(), command.getName()));
    }
}
