package fr.eris.argument.command.sub;

import fr.eris.argument.command.base.BaseCommandArgument;
import fr.eris.choice.command.ICommandChoice;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class StringCommandArgument extends BaseCommandArgument<String>
{
    public StringCommandArgument(@NotNull String name,
                                 @Nullable String description,
                                 @Nullable Collection<ICommandChoice> choices,
                                 boolean required, boolean requireValue) {
        super(name, description, choices, required, requireValue);
    }

    @Override
    public @Nullable String bake(@NotNull String argument) {
        return argument;
    }

    @Override
    public boolean isValid(@NotNull String argument) {
        return true;
    }
}
