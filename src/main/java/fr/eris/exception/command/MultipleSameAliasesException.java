package fr.eris.exception.command;

import fr.eris.command.ICommand;

public class MultipleSameAliasesException extends RuntimeException
{
    public MultipleSameAliasesException(ICommand command) {
        super(String.format("Command have same aliases multiple time. {name:'%s'}|{aliases:'%s'}",
                command.getName(), command.getAliases()));
    }
}
