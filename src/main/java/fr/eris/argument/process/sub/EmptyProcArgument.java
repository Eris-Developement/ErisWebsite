package fr.eris.argument.process.sub;

import fr.eris.argument.process.base.BaseProcArgument;

import java.util.Collection;

public class EmptyProcArgument extends BaseProcArgument<Void>
{
    public EmptyProcArgument(String name, boolean nameCaseSensitive,
                                String description, Collection<String> choices,
                                boolean required) {
        super(name, nameCaseSensitive, description, choices,
                required, false, null);
    }

    @Override
    public Void makeValue(String args) {
        return null;
    }

    @Override
    public boolean isArgumentValid(String args) {
        return true;
    }

    @Override
    public boolean isDefaultValueSet() {
        return false;
    }
}
