package fr.eris.exception.argument.process;

import fr.eris.argument.process.IProcArgument;

public class IllegalArgumentValueException extends RuntimeException
{
    public IllegalArgumentValueException(IProcArgument<?> argument) {
        super("Illegal argument value for the argument named: " + argument.getName());
    }
}
