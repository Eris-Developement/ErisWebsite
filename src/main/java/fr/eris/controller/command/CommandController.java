package fr.eris.controller.command;

import fr.eris.ErisWebsite;
import fr.eris.command.ICommand;
import fr.eris.command.sub.StopCommand;
import fr.eris.command.sub.TestCommand;
import fr.eris.completer.command.ErisCommandCompleter;
import fr.eris.controller.logger.LoggerController;
import fr.eris.exception.command.RegisteringSimilarCommandException;
import fr.eris.util.StringUtil;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jline.reader.Candidate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommandController implements ICommandController
{
    @Getter @NotNull private final Collection<ICommand> commands;

    private final ErisCommandCompleter completer;

    @Getter private ErisWebsite erisInstance;

    public CommandController() {
        commands = new ArrayList<>();
        completer = new ErisCommandCompleter(this);
    }

    @Nullable
    public ICommand findCommand(@NotNull String commandLabel) {
        for (ICommand command : commands) {
            if (command.isCommand(commandLabel)) {
                return command;
            }
        }
        return null;
    }

    @Nullable
    public ICommand findSimilarCommand(@NotNull ICommand command) {
        ICommand similarCommand = findCommand(command.getName());

        if (similarCommand != null)
            return similarCommand;

        if (command.getAliases() == null)
            return null;

        for (String commandAliases : command.getAliases()) {
            similarCommand = findCommand(commandAliases);

            if (similarCommand != null) {
                return similarCommand;
            }
        }
        return null;
    }

    public @NotNull Collection<ICommand> getCommandsStartingWith(@NotNull String input) {
        List<ICommand> allSimilarCommands = new ArrayList<>();
        input = input.toLowerCase();

        for (ICommand possibleSimilarCommand : commands) {
            if (possibleSimilarCommand.getName().startsWith(input)) {
                allSimilarCommands.add(possibleSimilarCommand);
                continue;
            }

            if (possibleSimilarCommand.getAliases() == null)
                continue;

            for (String commandAliases : possibleSimilarCommand.getAliases()) {
                if (commandAliases.startsWith(input)) {
                    allSimilarCommands.add(possibleSimilarCommand);
                    break;
                }
            }
        }
        return allSimilarCommands;
    }

    private void validateNoOtherSimilarCommands(@NotNull ICommand command) {
        ICommand similarCommand = findSimilarCommand(command);
        if (similarCommand != null) {
            throw new RegisteringSimilarCommandException(similarCommand, command);
        }
    }

    public void registerCommand(@NotNull ICommand command) {
        validateNoOtherSimilarCommands(command);
        commands.add(command);
    }

    public void complete(@NotNull String input, @NotNull List<Candidate> completeCandidates) {
        completer.complete(input, completeCandidates);
    }

    @Override
    public void processRawInput(@NotNull String input) {
        input = input.stripLeading();
        List<String> inputData = StringUtil.splitStringToSentence(input);
        List<String> arguments = StringUtil.createArgumentsList(inputData, input);

        if (arguments.size() > 1 && arguments.getLast().isBlank()) {
            arguments.removeLast();
        }
        String commandLabel = StringUtil.simplifyString(inputData.getFirst()).toLowerCase();

        erisInstance.getCommandController().executeCommand(commandLabel, arguments);
    }

    public void executeCommand(@NotNull String commandLabel, @NotNull List<String> arguments) {
        ICommand targetCommand = findCommand(commandLabel);

        if (targetCommand == null) {
            invalidCommandName(commandLabel);
            return;
        }
        if (!targetCommand.execute(commandLabel, arguments)) {
            LoggerController.DEFAULT.info(targetCommand.getUsage());
        }
    }

    private void invalidCommandName(@NotNull String input) {
        LoggerController.DEFAULT.infof("Invalid command name: '%s'", input);
    }

    private void registerCommands() {
        registerCommand(new StopCommand(this));
        registerCommand(new TestCommand(this));
    }

    public void load(ErisWebsite erisInstance) {
        this.erisInstance = erisInstance;
        this.registerCommands();
    }
}
