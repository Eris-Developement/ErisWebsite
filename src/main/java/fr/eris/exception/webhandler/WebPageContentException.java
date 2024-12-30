package fr.eris.exception.webhandler;

public class WebPageContentException extends RuntimeException
{
    public WebPageContentException(String path) {
        super(String.format("Error occur while trying to load the web page at: '%s'", path));
    }
}
