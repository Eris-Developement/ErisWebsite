package fr.eris.exception.server;

public class ServerLoadException extends RuntimeException
{
    public ServerLoadException() {
        super("Error on server loading.");
    }
}
