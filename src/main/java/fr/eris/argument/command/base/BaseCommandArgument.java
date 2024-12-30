package fr.eris.argument.command.base;

import fr.eris.argument.command.ICommandArgument;
import fr.eris.choice.command.ICommandChoice;
import fr.eris.exception.argument.command.IllegalArgumentNameException;
import fr.eris.util.StringUtil;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jline.reader.Candidate;

import java.util.Collection;

public abstract class BaseCommandArgument<T> implements ICommandArgument<T>
{
    @Getter protected final @NotNull String name;
    @Getter protected final @Nullable String description;
    @Getter protected final @Nullable Collection<ICommandChoice> choices;

    @Getter protected final boolean required;
    protected final boolean requireValue;

    protected BaseCommandArgument(@NotNull String name,
                                  @Nullable String description,
                                  @Nullable Collection<ICommandChoice> choices,
                                  boolean required,
                                  boolean requireValue) {
        this.name = name;
        this.description = description;
        this.choices = choices;
        this.required = required;
        this.requireValue = requireValue;
        validate();
    }

    private void validate() {
        if (name.contains(" "))
            throw new IllegalArgumentNameException(this, " ");
        if (required && !requireValue)
            throw new IllegalStateException("Cannot require the argument and not requires any value !");
    }

    public boolean requiresValue() {
        return requireValue;
    }

    public @NotNull String getActiveName() {
        return String.format("%s%s", ARGUMENT_PREFIX, name);
    }

    public @NotNull Candidate asCandidate() {
        return new Candidate(getActiveName(),
                String.format("%s", StringUtil.simplifyString(name)),
                null, description,
                null, null, true);
    }
}
