package fr.eris.exception.argument.command;

import fr.eris.argument.command.ICommandArgument;

public class ArgumentNotRegisteredException extends RuntimeException
{
    public ArgumentNotRegisteredException(ICommandArgument<?> argument) {
        super(String.format("Cannot get a argument if not registered. {name:'%s'}|{class:'%s'}",
                argument.getName(), argument.getClass().getName()));
    }
}
