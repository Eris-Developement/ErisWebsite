package fr.eris.exception.argument.process;

import fr.eris.argument.process.IProcArgument;

public class UnfoundedArgumentValueException extends RuntimeException
{
    public UnfoundedArgumentValueException(IProcArgument<?> argument) {
        super("Unfound argument value but required: " + argument.getName());
    }
}
