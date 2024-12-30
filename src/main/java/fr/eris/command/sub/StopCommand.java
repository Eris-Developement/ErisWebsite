package fr.eris.command.sub;

import fr.eris.command.base.BaseCommand;
import fr.eris.command.base.CommandExecutionData;
import fr.eris.controller.command.ICommandController;
import fr.eris.controller.logger.LoggerController;
import org.jline.reader.Candidate;

import java.io.IOException;
import java.util.List;

public class StopCommand extends BaseCommand
{
    public StopCommand(ICommandController controller) {
        super(controller, "stop", "Used to stop the server and the whole process", null, List.of("exit"));
    }

    @Override
    public boolean subExecute(CommandExecutionData data) {
        try {
            controller.getErisInstance().stop();
            LoggerController.DEFAULT.info("Successfully stopped the server.");
        } catch (IOException exception) {
            LoggerController.DEFAULT.severef("Failed to stop the server: \n%s", exception.getMessage());
        }
        return true;
    }

    @Override
    public void complete(String commandLabel, List<String> args, List<Candidate> completeCandidates) {

    }
}
