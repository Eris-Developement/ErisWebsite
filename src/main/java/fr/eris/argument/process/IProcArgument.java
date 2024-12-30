package fr.eris.argument.process;

import java.util.Collection;

public interface IProcArgument<T>
{
    String ARGUMENT_PREFIX = "--";

    String getName(); // The name of the process argument.
    boolean isNameCaseSensitive(); // Indicate if the name should be case-sensitive

    String getDescription(); // The description used for help arguments

    Collection<String> getChoices(); // Return the default choices of a process (null for no choices)
    T makeValue(String args); // Transform a String argument to a value of type <T>
    boolean isArgumentValid(String args); // Use to know if a raw value is valid or not.
    boolean isValueRequired(); // Use to indicate if a value is required.

    boolean isRequired(); // Indicate if the argument is required to start the process.

    T getDefaultValue(); // Usable only if isRequired is false.
    boolean isDefaultValueSet(); // Usable only if isRequired is false.
}
