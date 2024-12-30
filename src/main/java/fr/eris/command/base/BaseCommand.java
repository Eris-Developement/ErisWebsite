package fr.eris.command.base;

import fr.eris.argument.command.ICommandArgument;
import fr.eris.choice.command.ICommandChoice;
import fr.eris.command.ICommand;
import fr.eris.controller.command.ICommandController;
import fr.eris.exception.command.MultipleSameAliasesException;
import fr.eris.exception.argument.command.MultipleSameArgumentNameException;
import fr.eris.exception.command.SameCommandAliasesAndNameException;
import fr.eris.util.StringUtil;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jline.reader.Candidate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class BaseCommand implements ICommand
{
    @Getter protected final @NotNull String name;
    @Getter protected final @NotNull Collection<ICommandArgument<?>> arguments;

    @Getter protected final @Nullable String description;
    protected final @Nullable String usage;

    @Getter protected final @Nullable List<@NotNull String> aliases;

    protected final @NotNull ICommandController controller;

    public BaseCommand(@NotNull ICommandController controller,
                       @NotNull String name,
                       @Nullable String description,
                       @Nullable String usage,
                       @Nullable List<@NotNull String> aliases) {
        this.controller = controller;
        this.name = name.toLowerCase();
        this.description = description;
        this.usage = usage;
        this.aliases = aliases != null ? new ArrayList<>(aliases) : null;
        this.arguments = new ArrayList<>();

        fixAliases();
    }

    public @NotNull String getUsage() {
        if (usage != null) {
            return usage;
        }
        return String.format("<%s>", name);
    }

    private void fixAliases() {
        if (aliases == null)
            return;
        aliases.replaceAll(String::toLowerCase);

        if (aliases.contains(name))
            throw new SameCommandAliasesAndNameException(this);

        for (String alias : aliases) {
            if (Collections.frequency(aliases, alias) <= 1) {
                continue;
            }
            throw new MultipleSameAliasesException(this);
        }
    }

    public boolean isCommand(@NotNull String commandLabel) {
        commandLabel = commandLabel.toLowerCase();

        return name.equals(commandLabel) ||
                (aliases != null && aliases.contains(commandLabel));
    }

    public boolean hasAliases() {
        return aliases != null && !aliases.isEmpty();
    }

    public @NotNull Candidate asCandidate(@NotNull String commandLabel) {
        return new Candidate(commandLabel,
                String.format("%s", StringUtil.simplifyString(commandLabel)),
                null, getDescription(),
                null, null, true);
    }

    @Override
    public void addArgument(@NotNull ICommandArgument<?> newArgument) {
        for (ICommandArgument<?> actualArgument : arguments) {
            if (actualArgument.getName().equals(newArgument.getName())) {
                throw new MultipleSameArgumentNameException(newArgument, actualArgument);
            }
        }
        arguments.add(newArgument);
    }

    @Override
    public @Nullable ICommandArgument<?> getArgument(@NotNull String name) {
        name = name.toLowerCase();
        for (ICommandArgument<?> argument : arguments) {
            if (argument.getName().equals(name)) {
                return argument;
            }
        }
        return null;
    }

    public abstract boolean subExecute(CommandExecutionData data);

    public final boolean execute(String commandLabel, List<String> commandArguments) {
        CommandExecutionData data = new CommandExecutionData(this, commandLabel, commandArguments);

        if (!data.validate()) {
            return false;
        }
        return subExecute(data);
    }

    private @Nullable ICommandArgument<?> findLastUsedArgument(List<String> args) {
        ICommandArgument<?> lastUsedArgument = null;

        for (int i = args.size() - 2; i >= 0; i--) {
            String previousLastArgument = args.get(i);
            if (!previousLastArgument.startsWith(ICommandArgument.ARGUMENT_PREFIX))
                continue;
            lastUsedArgument = getArgument(previousLastArgument.substring(ICommandArgument.ARGUMENT_PREFIX.length()));
        }
        return lastUsedArgument;
    }

    public void complete(String commandLabel, List<String> args, List<Candidate> completeCandidates) {
        String lastArgument = args.getLast();

        if (args.size() == 1 || lastArgument.startsWith(ICommandArgument.ARGUMENT_PREFIX)) {
            if (lastArgument.startsWith(ICommandArgument.ARGUMENT_PREFIX))
                lastArgument = lastArgument.substring(ICommandArgument.ARGUMENT_PREFIX.length());
            for (ICommandArgument<?> argument : arguments) {
                if (argument.getName().startsWith(lastArgument)) {
                    completeCandidates.add(argument.asCandidate());
                }
            }
            return;
        }

        ICommandArgument<?> targetArgument = findLastUsedArgument(args);
        if (targetArgument == null || targetArgument.getChoices() == null)
            return;
        for (ICommandChoice choice : targetArgument.getChoices()) {
            if (choice.getValue().startsWith(lastArgument)) {
                completeCandidates.add(choice.asCandidate());
            }
        }
    }
}
