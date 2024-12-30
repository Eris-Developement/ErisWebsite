package fr.eris.argument.command;

import fr.eris.choice.command.ICommandChoice;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jline.reader.Candidate;

import java.util.Collection;

public interface ICommandArgument<T>
{
    @NotNull String ARGUMENT_PREFIX = "-";

    @NotNull String getActiveName();
    @NotNull String getName();
    @Nullable String getDescription();

    @Nullable Collection<ICommandChoice> getChoices();

    boolean isRequired();
    boolean requiresValue();

    @Nullable T bake(@NotNull String argument);
    boolean isValid(@NotNull String argument);

    @NotNull Candidate asCandidate();
}
