package fr.eris.argument.command.sub;

import fr.eris.argument.command.base.BaseCommandArgument;
import fr.eris.choice.command.ICommandChoice;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class EmptyCommandArgument extends BaseCommandArgument<Void>
{
    public EmptyCommandArgument(@NotNull String name,
                                   @Nullable String description,
                                   @Nullable Collection<ICommandChoice> choices,
                                   boolean required) {
        super(name, description, choices, required, false);
    }

    @Override
    public @Nullable Void bake(@NotNull String argument) {
        return null;
    }

    @Override
    public boolean isValid(@NotNull String argument) {
        return true;
    }
}
