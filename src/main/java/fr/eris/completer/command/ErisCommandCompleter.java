package fr.eris.completer.command;

import fr.eris.command.ICommand;
import fr.eris.controller.command.ICommandController;
import fr.eris.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jline.reader.Candidate;

import java.util.ArrayList;
import java.util.List;

public class ErisCommandCompleter
{
    private final ICommandController commandController;

    public ErisCommandCompleter(ICommandController commandController) {
        this.commandController = commandController;
    }

    private void addCandidate(@NotNull List<Candidate> inputData, @NotNull String input, @NotNull ICommand command) {
        if (input.contains(" "))
            input = String.format("'%s'", input);
        inputData.add(command.asCandidate(input));
    }

    private @NotNull List<Candidate> findSimilarCommandCandidate(@NotNull String commandLabel) {
        List<Candidate> completeCandidates = new ArrayList<>();

        for (ICommand command : commandController.getCommandsStartingWith(commandLabel)) {
            if (command.getName().startsWith(commandLabel)) {
                addCandidate(completeCandidates, command.getName(), command);
            }

            if (command.getAliases() == null)
                continue;

            for (String commandAliases : command.getAliases()) {
                if (!(commandAliases.startsWith(commandLabel)))
                    continue;
                addCandidate(completeCandidates, commandAliases, command);
            }
        }
        return completeCandidates;
    }

    public void complete(@NotNull String input, @NotNull List<Candidate> completeCandidates) {
        input = input.stripLeading();
        List<String> inputData = StringUtil.splitStringToSentence(input);
        List<String> arguments = StringUtil.createArgumentsList(inputData, input);

        String commandLabel = inputData.isEmpty() ? "" : StringUtil.simplifyString(inputData.getFirst()).toLowerCase();
        ICommand targetCommand = commandController.findCommand(commandLabel.toLowerCase());

        if (targetCommand != null && !arguments.isEmpty()) {
            targetCommand.complete(commandLabel, arguments, completeCandidates);
            return;
        }
        if (arguments.isEmpty()) {
            completeCandidates.addAll(findSimilarCommandCandidate(commandLabel));
        }
    }
}
