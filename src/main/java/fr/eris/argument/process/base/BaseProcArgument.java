package fr.eris.argument.process.base;

import fr.eris.argument.process.IProcArgument;
import lombok.Getter;

import java.util.Collection;

@Getter
public abstract class BaseProcArgument<T> implements IProcArgument<T>
{
    private final String name;
    private final boolean nameCaseSensitive;

    private final String description;
    private final Collection<String> choices;

    private final boolean required;
    private final boolean valueRequired;
    private final T defaultValue;

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
