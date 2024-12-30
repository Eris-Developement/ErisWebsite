package fr.eris.argument.process.base;

import fr.eris.argument.process.IProcArgument;
import lombok.Getter;

import java.util.Collection;

public abstract class BaseProcArgument<T> implements IProcArgument<T>
{
    @Getter private final String name;
    @Getter private final boolean nameCaseSensitive;

    @Getter private final String description;
    @Getter private final Collection<String> choices;

    @Getter private final boolean required;
    @Getter private final boolean valueRequired;
    @Getter private final T defaultValue;

    protected BaseProcArgument(String name, boolean nameCaseSensitive,
                               String description, Collection<String> choices,
                               boolean required, boolean valueRequired, T defaultValue) {
        this.name = name;
        this.nameCaseSensitive = nameCaseSensitive;
        this.description = description;
        this.choices = choices;
        this.required = required;
        this.valueRequired = valueRequired;
        this.defaultValue = defaultValue;
    }
}
