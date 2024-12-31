package fr.eris.controller.input;

import fr.eris.ErisWebsite;
import org.jline.reader.LineReader;

import java.io.IOException;

public interface IInputController
{
    ErisWebsite getErisInstance();

    void load(ErisWebsite erisInstance) throws IOException;
    boolean isLoaded();
    void close() throws IOException;
    boolean isClosed();

    boolean isListening();
    void listen();

    void processNewInput(String input);

    LineReader getLineReader();
}
