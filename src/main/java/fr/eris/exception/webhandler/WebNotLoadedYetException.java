package fr.eris.exception.webhandler;

public class WebNotLoadedYetException extends RuntimeException
{
    public WebNotLoadedYetException() {
        super("You cannot do that if the isn't loaded.");
    }
}
