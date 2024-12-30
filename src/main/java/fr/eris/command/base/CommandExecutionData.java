package fr.eris.command.base;

import fr.eris.argument.command.ICommandArgument;
import fr.eris.command.ICommand;
import fr.eris.controller.logger.LoggerController;
import fr.eris.exception.argument.command.ArgumentNotRegisteredException;
import fr.eris.util.StringUtil;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CommandExecutionData
{
    private final ICommand command;
    @Getter private final String commandLabel;
    @Getter private final List<String> arguments;

    CommandExecutionData(ICommand command, String commandLabel, List<String> arguments) {
        this.command = command;
        this.commandLabel = commandLabel;
        this.arguments = arguments;
    }

    boolean validate() {
        for (ICommandArgument<?> argument : command.getArguments()) {
            if (argument.isRequired() && !isSet(argument)) {
                LoggerController.DEFAULT.info("1");
                return false;
            }
        }

        ICommandArgument<?> commandArgument = null;
        Iterator<String> argumentIterator = arguments.iterator();
        while (argumentIterator.hasNext()) {
            String argument = argumentIterator.next();
            if (argument.startsWith(ICommandArgument.ARGUMENT_PREFIX)) {
                commandArgument = command.getArgument(argument.substring(1));
                argument = argumentIterator.hasNext() ? argumentIterator.next() : null;
                if (commandArgument != null) {
                    if (commandArgument.requiresValue() && (argument == null || argument.startsWith(ICommandArgument.ARGUMENT_PREFIX))) {
                        LoggerController.DEFAULT.info("2");
                        return false;
                    }
                }
            }
            if (commandArgument == null) {
                LoggerController.DEFAULT.info("3");
                return false;
            }
            if (argument != null && !commandArgument.isValid(StringUtil.simplifyString(argument))) {
                LoggerController.DEFAULT.info("4");
                return false;
            }
        }
        return true;
    }

    public boolean isSet(ICommandArgument<?> commandArgument) {
        if (!command.getArguments().contains(commandArgument)) {
            throw new ArgumentNotRegisteredException(commandArgument);
        }
        String argumentActiveName = commandArgument.getActiveName();

        for (String argument : arguments) {
            if (argument.equalsIgnoreCase(argumentActiveName)) {
                return true;
            }
        }
        return false;
    }

    public <T> @Nullable T getValue(ICommandArgument<T> commandArgument) {
        List<T> values = getValues(commandArgument);

        return values != null && !values.isEmpty() ? values.getFirst() : null;
    }

    public <T> @Nullable List<T> getValues(ICommandArgument<T> commandArgument) {
        if (!command.getArguments().contains(commandArgument)) {
            throw new ArgumentNotRegisteredException(commandArgument);
        }
        if (!isSet(commandArgument)) {
            return null;
        }
        List<T> values = new ArrayList<>();
        ListIterator<String> argumentIterator = arguments.listIterator();

        while (argumentIterator.hasNext()) {
            String argument = argumentIterator.next();
            if (!argument.equalsIgnoreCase(commandArgument.getActiveName())) {
                continue;
            }
            while (argumentIterator.hasNext()) {
                argument = argumentIterator.next();
                if (argument.startsWith(ICommandArgument.ARGUMENT_PREFIX)) {
                    argumentIterator.previous();
                    break;
                }
                values.add(commandArgument.bake(StringUtil.simplifyString(argument)));
            }
        }
        if (values.isEmpty())
            return null;
        return values;
    }
}
