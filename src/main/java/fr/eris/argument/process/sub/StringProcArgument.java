package fr.eris.argument.process.sub;

import fr.eris.argument.process.base.BaseProcArgument;
import lombok.Getter;

import java.util.Collection;

public class StringProcArgument extends BaseProcArgument<String>
{
    @Getter private final boolean isDefaultValueSet;

    public StringProcArgument(String name, boolean nameCaseSensitive,
                              String description, Collection<String> choices,
                              boolean required, boolean valueRequired) {
        super(name, nameCaseSensitive, description, choices,
                required, valueRequired, null);
        this.isDefaultValueSet = false;
    }

    public StringProcArgument(String name, boolean nameCaseSensitive,
                              String description, Collection<String> choices,
                              boolean required, boolean valueRequired,
                              String defaultValue) {
        super(name, nameCaseSensitive, description, choices,
                required, valueRequired, defaultValue);
        this.isDefaultValueSet = true;
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
