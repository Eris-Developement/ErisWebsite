package fr.eris.controller.command;

import fr.eris.ErisWebsite;
import fr.eris.command.ICommand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jline.reader.Candidate;

import java.util.Collection;
import java.util.List;

public interface ICommandController
{
    ErisWebsite getErisInstance();
    @NotNull Collection<ICommand> getCommands();

    void load(ErisWebsite erisInstance);

    @Nullable ICommand findCommand(@NotNull String commandLabel);
    @Nullable ICommand findSimilarCommand(@NotNull ICommand command);

    void registerCommand(@NotNull ICommand command);

    void processRawInput(@NotNull String input);
    void complete(@NotNull String input, @NotNull List<Candidate> completeCandidates);

    void executeCommand(@NotNull String input, @NotNull List<String> arguments);

    @NotNull Collection<ICommand> getCommandsStartingWith(@NotNull String input);
}
