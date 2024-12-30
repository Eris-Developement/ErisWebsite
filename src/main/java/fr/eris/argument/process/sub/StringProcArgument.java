package fr.eris.argument.process.sub;

import fr.eris.argument.process.base.BaseProcArgument;
import lombok.Getter;

import java.util.Collection;

@Getter
public class StringProcArgument extends BaseProcArgument<String>
{
    private final boolean defaultValueSet;

    public StringProcArgument(String name, boolean nameCaseSensitive,
                              String description, Collection<String> choices,
                              boolean required, boolean valueRequired) {
        super(name, nameCaseSensitive, description, choices,
                required, valueRequired, null);
        this.defaultValueSet = false;
    }

    public StringProcArgument(String name, boolean nameCaseSensitive,
                              String description, Collection<String> choices,
                              boolean required, boolean valueRequired,
                              String defaultValue) {
        super(name, nameCaseSensitive, description, choices,
                required, valueRequired, defaultValue);
        this.defaultValueSet = true;
    }

    @Override
    public String makeValue(String args) {
        return args;
    }

    @Override
    public boolean isArgumentValid(String args) {
        return true;
    }
}
