package fr.eris.argument.process.sub;

import fr.eris.argument.process.base.BaseProcArgument;
import lombok.Getter;

import java.util.Collection;

@Getter
public class ShortProcArgument extends BaseProcArgument<Short>
{
    private final boolean defaultValueSet;

    public ShortProcArgument(String name, boolean nameCaseSensitive,
                             String description, Collection<String> choices,
                             boolean required, boolean valueRequired) {
        super(name, nameCaseSensitive, description, choices,
                required, valueRequired, null);
        this.defaultValueSet = false;
    }

    public ShortProcArgument(String name, boolean nameCaseSensitive,
                             String description, Collection<String> choices,
                             boolean required, boolean valueRequired,
                             Short defaultValue) {
        super(name, nameCaseSensitive, description, choices,
                required, valueRequired, defaultValue);
        this.defaultValueSet = true;
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
