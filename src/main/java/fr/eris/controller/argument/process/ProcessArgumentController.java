package fr.eris.controller.argument.process;

import fr.eris.argument.process.IProcArgument;
import fr.eris.controller.logger.LoggerController;
import fr.eris.exception.argument.process.IllegalArgumentValueException;
import fr.eris.exception.argument.process.UnfoundedArgumentException;
import fr.eris.exception.argument.process.UnfoundedArgumentValueException;
import lombok.Getter;

import java.util.*;

public class ProcessArgumentController implements IProcessArgumentController
{
    private final List<IProcArgument<?>> arguments;
    @Getter private final boolean usingHelp;

    private final Map<IProcArgument<?>, String> argumentRawValue;

    public ProcessArgumentController(boolean usingHelp) {
        this.usingHelp = usingHelp;
        this.arguments = new ArrayList<>();

        if (usingHelp) {
            registerHelpArgument();
        }

        this.argumentRawValue = new HashMap<>();
    }

    private void registerHelpArgument() {
        registerArgument(HELP_ARGUMENT);
    }

    @Override
    public void registerArgument(IProcArgument<?> argument) {
        arguments.add(argument);
    }

    private IProcArgument<?> getArgumentByName(String name) {
        for (IProcArgument<?> argument : arguments) {
            if (!((argument.isNameCaseSensitive() && argument.getName().equals(name))
                    || argument.getName().equalsIgnoreCase(name))) {
                continue;
            }
            return argument;
        }
        return null;
    }

    @Override
    public void loadRawArguments(String[] rawArguments) {
        this.argumentRawValue.clear();
        ListIterator<String> rawArgumentIterator = Arrays.asList(rawArguments).listIterator();
        while (rawArgumentIterator.hasNext()) {
            String rawArgument = rawArgumentIterator.next();
            if (!rawArgument.startsWith(IProcArgument.ARGUMENT_PREFIX)) {
                continue;
            }
            IProcArgument<?> currentArgument = getArgumentByName(rawArgument.substring(IProcArgument.ARGUMENT_PREFIX.length()));
            if (currentArgument == null) {
                continue;
            }
            String argumentValue = rawArgumentIterator.hasNext() ? rawArgumentIterator.next() : null;
            if (argumentValue != null && argumentValue.startsWith(IProcArgument.ARGUMENT_PREFIX)) {
                rawArgumentIterator.previous();
                argumentValue = null;
            }
            argumentRawValue.put(currentArgument, argumentValue);
        }
        validateArgumentRawValue();
    }

    private void validateArgumentRawValue() {
        for (IProcArgument<?> argument : arguments) {
            if (argument.isRequired() && !argumentRawValue.containsKey(argument))
                throw new UnfoundedArgumentException(argument);
            if (argumentRawValue.containsKey(argument) && argument.isValueRequired() && argumentRawValue.get(argument) == null)
                throw new UnfoundedArgumentValueException(argument);
            if (argumentRawValue.containsKey(argument) && !argument.isArgumentValid(argumentRawValue.get(argument)))
                throw new IllegalArgumentValueException(argument);
        }
    }

    @Override
    public boolean isSet(IProcArgument<?> argument) {
        return argumentRawValue.containsKey(argument);
    }

    @Override
    public <T> T getArgumentValue(IProcArgument<T> argument) {
        if (argumentRawValue.containsKey(argument))
            return argument.makeValue(argumentRawValue.get(argument));
        else if (argument.isDefaultValueSet())
            return argument.getDefaultValue();
        throw new UnfoundedArgumentException(argument);
    }

    @Override
    public void showArgumentHelp() {
        LoggerController.DEFAULT.info("+---+");
        LoggerController.DEFAULT.info("Arguments help:");
        for (IProcArgument<?> argument : arguments) {
            LoggerController.DEFAULT.info("  " + argument.getName());
            if (argument.getDescription() != null)
                LoggerController.DEFAULT.info("   |-- Description --> " + argument.getDescription());
            if (argument.getChoices() != null)
                LoggerController.DEFAULT.info("   |---- Choices ----> " + argument.getChoices());
            if (argument.isDefaultValueSet())
                LoggerController.DEFAULT.info("   |- Default Value ->  " + argument.getDefaultValue());
        }
        LoggerController.DEFAULT.info("+---+");
    }
}
