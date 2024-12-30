package fr.eris.command;

import fr.eris.argument.command.ICommandArgument;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jline.reader.Candidate;

import java.util.Collection;
import java.util.List;

public interface ICommand
{
    @NotNull String getName();

    @Nullable String getDescription();
    @NotNull String getUsage();

    @Nullable List<@NotNull String> getAliases();
    boolean hasAliases();

    @NotNull Collection<ICommandArgument<?>> getArguments();
    @Nullable ICommandArgument<?> getArgument(@NotNull String name);
    void addArgument(@NotNull ICommandArgument<?> newArgument);

    /**
     * Use to execute a command
     * @return true if the command succeed else return false and should print usage.
     */
    boolean execute(String commandLabel, List<String> commandArgument);
    void complete(String commandLabel, List<String> args, List<Candidate> completeCandidates);

    boolean isCommand(@NotNull String commandLabel);

    @NotNull Candidate asCandidate(@NotNull String commandLabel);
}
