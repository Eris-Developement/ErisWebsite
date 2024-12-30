package fr.eris.argument.process.sub;

import fr.eris.argument.process.base.BaseProcArgument;
import lombok.Getter;

import java.util.Collection;

public class ShortProcArgument extends BaseProcArgument<Short>
{
    @Getter private final boolean isDefaultValueSet;

    public ShortProcArgument(String name, boolean nameCaseSensitive,
                             String description, Collection<String> choices,
                             boolean required, boolean valueRequired) {
        super(name, nameCaseSensitive, description, choices,
                required, valueRequired, null);
        this.isDefaultValueSet = false;
    }

    public ShortProcArgument(String name, boolean nameCaseSensitive,
                             String description, Collection<String> choices,
                             boolean required, boolean valueRequired,
                             Short defaultValue) {
        super(name, nameCaseSensitive, description, choices,
                required, valueRequired, defaultValue);
        this.isDefaultValueSet = true;
    }

    @Override
    public Short makeValue(String args) {
        return Short.parseShort(args);
    }

    @Override
    public boolean isArgumentValid(String args) {
        try {
            Short.parseShort(args);
            return true;
        } catch (NumberFormatException _) {
            return false;
        }
    }
}
