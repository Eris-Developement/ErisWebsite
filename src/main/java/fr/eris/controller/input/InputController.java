package fr.eris.controller.input;

import fr.eris.ErisWebsite;
import fr.eris.completer.input.ErisInputCompleter;
import fr.eris.controller.logger.LoggerController;
import fr.eris.exception.input.InputControllerAlreadyClosedException;
import fr.eris.exception.input.InputControllerAlreadyLoadedException;
import fr.eris.exception.input.InputControllerClosedException;
import fr.eris.exception.input.InputControllerNotLoadedException;
import fr.eris.util.ReflectionUtil;
import lombok.Getter;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class InputController implements IInputController
{
    @Getter private boolean listening;
    @Getter private boolean loaded;
    @Getter private boolean closed;

    private Terminal terminal;
    @Getter private LineReader lineReader;
    @Getter private String lastInput;

    @Getter private ErisWebsite erisInstance;

    public void load(ErisWebsite erisInstance) throws IOException {
        if (loaded && !closed)
            throw new InputControllerAlreadyLoadedException();

        this.erisInstance = erisInstance;
        terminal = TerminalBuilder.builder().system(true).build();
        lineReader = LineReaderBuilder.builder()
                .completer(new ErisInputCompleter(this))
                .highlighter(new ErisInputHighlighter())
                .terminal(terminal).build();
        loaded = true;
        closed = false;
    }

    public void close() throws IOException {
        if (!loaded)
            throw new InputControllerNotLoadedException();
        if (closed)
            throw new InputControllerAlreadyClosedException();
        LoggerController.DEFAULT.info("Closing input controller...");


        terminal.close();
        listening = false;
        closed = true;
        LoggerController.DEFAULT.info("Input controller closed.");
    }

    public void listen() throws IOException {
        if (!loaded)
            throw new InputControllerNotLoadedException();
        if (closed)
            throw new InputControllerClosedException();

        listening = true;
        LoggerController.DEFAULT.info("Start waiting for user input.");
        while (listening) {
            lastInput = askInput();
            processNewInput(lastInput);
        }
    }

    public void processNewInput(String input) {
        if (!loaded)
            throw new InputControllerNotLoadedException();
        if (closed)
            throw new InputControllerClosedException();
        if (input.isBlank())
            return;

        StackTraceElement caller = ReflectionUtil.getCallerTrace();

        StringBuilder log = new StringBuilder();

        if (!(caller.getClassName().equals(InputController.class.getName())))
            log.append(String.format("Forced Input from [%s#%s:%d]", caller.getClassName(), caller.getMethodName(), caller.getLineNumber()));
        else log.append("Admin Input");
        log.append(": '").append(input).append("'");

        LoggerController.DEFAULT.info(log.toString());

        erisInstance.getCommandController().processRawInput(input);
    }

    private String askInput() {
        return lineReader.readLine("> ");
    }
}
