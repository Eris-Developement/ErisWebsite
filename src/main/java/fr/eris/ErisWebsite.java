package fr.eris;

import fr.eris.application.ErisWebApplication;
import fr.eris.application.IErisWebApplication;
import fr.eris.argument.process.IProcArgument;
import fr.eris.argument.process.sub.ShortProcArgument;
import fr.eris.controller.argument.process.ProcessArgumentController;
import fr.eris.controller.argument.process.IProcessArgumentController;
import fr.eris.controller.command.CommandController;
import fr.eris.controller.command.ICommandController;
import fr.eris.controller.input.IInputController;
import fr.eris.controller.input.InputController;
import fr.eris.controller.logger.ILoggerController;
import fr.eris.controller.logger.LoggerController;
import lombok.Getter;

import java.io.IOException;

public class ErisWebsite
{
    @Getter private IProcessArgumentController argumentController;
    @Getter private IErisWebApplication webApplication;
    @Getter private IInputController inputController;
    @Getter private ILoggerController loggerController;
    @Getter private ICommandController commandController;

    private final IProcArgument<Short> WEB_SERVER_PORT_ARGUMENT = new ShortProcArgument("WebServerPort", false,
            "The port for the web server.", null, false, true, (short)8080);

    private void registerArgument() {
        argumentController.registerArgument(WEB_SERVER_PORT_ARGUMENT);
    }

    public void argumentLoading(String[] args) {
        argumentController = new ProcessArgumentController(true);
        registerArgument();
        argumentController.loadRawArguments(args);
        if (argumentController.isSet(IProcessArgumentController.HELP_ARGUMENT)) {
            argumentController.showArgumentHelp();
        }
    }

    public void load(String[] args) throws IOException {
        System.out.println("Loading ErisWebsite logger !");
        loggerController = new LoggerController();
        loggerController.load(this);

        System.out.println("Logger successfully loaded.");

        inputController = new InputController();
        inputController.load(this);
        LoggerController.DEFAULT.info("Input controller successfully loaded.");

        argumentLoading(args);
        LoggerController.DEFAULT.info("Argument system successfully loaded.");

        webApplication = new ErisWebApplication();
        webApplication.load(this);
        LoggerController.DEFAULT.info("Web application successfully loaded.");

        commandController = new CommandController();
        commandController.load(this);
        LoggerController.DEFAULT.info("Command system successfully loaded.");

        LoggerController.DEFAULT.info("Finish loading of ErisWebsite !");
    }

    public void start() throws IOException {
        webApplication.start(argumentController.getArgumentValue(WEB_SERVER_PORT_ARGUMENT));

        inputController.listen();
    }

    public void stop() throws IOException {
        webApplication.stop();
        loggerController.close();
        inputController.close();
    }
}