package fr.eris.command.sub;

import fr.eris.argument.command.sub.EmptyCommandArgument;
import fr.eris.argument.command.sub.StringCommandArgument;
import fr.eris.choice.command.CommandChoice;
import fr.eris.command.base.BaseCommand;
import fr.eris.command.base.CommandExecutionData;
import fr.eris.controller.command.ICommandController;
import fr.eris.controller.logger.LoggerController;

import java.util.Arrays;
import java.util.List;

public class TestCommand extends BaseCommand
{

    private static final EmptyCommandArgument ACTIVE_ARGUMENT = new EmptyCommandArgument("active", "Should be active", null, false);
    private static final StringCommandArgument SAY_ARGUMENT = new StringCommandArgument("say", "Things to say",
            Arrays.asList(
                    CommandChoice.builder().value("TestValue").description("Some desc").display("TestValueDisp").build(),
                    CommandChoice.builder().value("TestValue2").description("Some desc2").display("TestValueDisp2").build(),
                    CommandChoice.builder().value("TestValue3").description("Some desc3").display("TestValueDisp3").build(),
                    CommandChoice.builder().value("Papa").description("Some desc Papa").display("TestValuePapa").build(),
                    CommandChoice.builder().value("Maman").description("Some desc Maman").display("TestValueMaman").build()),
            false, false);

    public TestCommand(ICommandController controller) {
        super(controller, "test", "Use to test some functionality", null, null);

        addArgument(ACTIVE_ARGUMENT);
        addArgument(SAY_ARGUMENT);
    }

    @Override
    public boolean subExecute(CommandExecutionData data) {
        if (data.isSet(ACTIVE_ARGUMENT)) {
            LoggerController.DEFAULT.info("Logging as INFO");
            LoggerController.DEFAULT.warning("Logging as WARNING");
            LoggerController.DEFAULT.severe("Logging as SEVERE");
            LoggerController.DEFAULT.fine("Logging as FINE");
            LoggerController.DEFAULT.finer("Logging as FINER");
            LoggerController.DEFAULT.finest("Logging as FINEST");
            LoggerController.DEFAULT.config("Logging as CONFIG");
        }
        if (data.isSet(SAY_ARGUMENT)) {
            List<String> toSay = data.getValues(SAY_ARGUMENT);
            LoggerController.DEFAULT.info("I'm gonna say something.");
            if (toSay == null) {
                LoggerController.DEFAULT.info("or not >:).");
            } else {
                toSay.forEach((string) -> LoggerController.DEFAULT.infof("Saying: '%s'", string));
            }
        }
        return true;
    }
}
