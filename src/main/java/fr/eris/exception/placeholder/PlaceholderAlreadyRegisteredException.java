package fr.eris.exception.placeholder;

public class PlaceholderAlreadyRegisteredException extends RuntimeException
{
    public PlaceholderAlreadyRegisteredException(String placeholderName) {
        super(String.format("Placeholder '%s' already registered", placeholderName));
    }
}
