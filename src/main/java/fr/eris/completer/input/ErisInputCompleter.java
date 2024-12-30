package fr.eris.completer.input;

import fr.eris.controller.command.ICommandController;
import fr.eris.controller.input.IInputController;
import fr.eris.controller.logger.LoggerController;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import java.util.List;

public class ErisInputCompleter implements Completer
{
    private final IInputController controller;

    public ErisInputCompleter(IInputController controller) {
        this.controller = controller;
    }

    @Override
    public void complete(LineReader lineReader, ParsedLine parsedLine, List<Candidate> completeCandidates) {
        ICommandController commandController = controller.getErisInstance().getCommandController();
        String line = parsedLine.line();

        LoggerController.DEFAULT.infof("New completer request: '%s'", line);
        commandController.complete(line, completeCandidates);
    }
}
