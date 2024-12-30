package fr.eris.exception.argument.process;

import fr.eris.argument.process.IProcArgument;

public class UnfoundedArgumentException extends RuntimeException
{
    public UnfoundedArgumentException(IProcArgument<?> argument) {
        super("Unfound argument but required: " + argument.getName());
    }
}
