package fr.eris.exception.argument.command;

import fr.eris.argument.command.ICommandArgument;

public class MultipleSameArgumentNameException extends RuntimeException
{
    public MultipleSameArgumentNameException(ICommandArgument<?> newArgument, ICommandArgument<?> currentArgument) {
        super(String.format("Command already have argument with same name. {name:'%s'}|{new_class:'%s'}|{current_class:%s}",
                newArgument.getName(), newArgument.getClass().getName(), currentArgument.getClass().getName()));
    }
}
