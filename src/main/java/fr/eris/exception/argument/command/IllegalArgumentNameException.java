package fr.eris.exception.argument.command;

import fr.eris.argument.command.ICommandArgument;

public class IllegalArgumentNameException extends RuntimeException
{
    public IllegalArgumentNameException(ICommandArgument<?> argument, String illegalContent) {
        super(String.format("Illegal argument name. {name:'%s'}|{illegal_content:'%s'}",
                argument.getName(), illegalContent));
    }
}
