package fr.eris.controller.argument.process;

import fr.eris.argument.process.IProcArgument;
import fr.eris.argument.process.sub.EmptyProcArgument;

public interface IProcessArgumentController
{
    IProcArgument<?> HELP_ARGUMENT = new EmptyProcArgument("Help", false,
                    "Use the show help over every registered process argument.", null, false);

    boolean isUsingHelp();

    void registerArgument(IProcArgument<?> argument);
    void loadRawArguments(String[] rawArguments);

    boolean isSet(IProcArgument<?> argument);
    <T> T getArgumentValue(IProcArgument<T> argument);

    void showArgumentHelp();
}
