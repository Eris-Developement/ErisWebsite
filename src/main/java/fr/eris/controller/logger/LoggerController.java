package fr.eris.controller.logger;

import fr.eris.ErisWebsite;
import fr.eris.configuration.logger.ILoggerConfiguration;
import fr.eris.configuration.logger.LoggerConfiguration;
import fr.eris.exception.logger.LoggerControllerAlreadyLoadedException;
import fr.eris.exception.logger.LoggerControllerNotLoadedException;
import fr.eris.logger.ErisLogger;
import fr.eris.formatter.logger.sub.ErisBasicLoggerFormatter;
import fr.eris.logger.IErisLogger;
import fr.eris.util.DateFormatter;
import fr.eris.util.Function2;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class LoggerController implements ILoggerController
{
    public static final Function2<IErisLogger, String, String> LOGGER_PLACEHOLDER = (logger, value) ->
        value.replace("{logger_name}", logger.getName())
             .replace("{lc_logger_name}", logger.getName().toLowerCase());

    private static final ILoggerConfiguration DEFAULT_CONFIG = LoggerConfiguration.builder()
                    .usingConsoleHandler(new ErisBasicLoggerFormatter())
                    .usingFileHandler(String.format("logs/%s/{lc_logger_name}.log", DateFormatter.of("dd-MM-yyyy")), new ErisBasicLoggerFormatter())
                    .build();

    public static final String DEFAULT_LOGGER_NAME = ErisWebsite.class.getSimpleName();
    public static IErisLogger DEFAULT;

    private static final HashMap<String, IErisLogger> REGISTERED_LOGGER = new HashMap<>();

    @Nullable private static ErisWebsite ERIS_INSTANCE;

    public void load(ErisWebsite erisInstance) {
        if (ERIS_INSTANCE != null) {
            throw new LoggerControllerAlreadyLoadedException();
        }
        ERIS_INSTANCE = erisInstance;
        DEFAULT = of(DEFAULT_LOGGER_NAME, DEFAULT_CONFIG);
    }

    public void close() {
        if (ERIS_INSTANCE == null) {
            throw new LoggerControllerNotLoadedException();
        }
        for (IErisLogger registeredLogger : REGISTERED_LOGGER.values()) {
            registeredLogger.infof("Closing: '%s'", registeredLogger.getName());
            registeredLogger.close();
        }
    }

    public static IErisLogger of(Class<?> clazz) {
        return of(clazz.getName());
    }

    public static IErisLogger of(Class<?> clazz, ILoggerConfiguration config) {
        return of(clazz.getName(), config);
    }

    public static IErisLogger of(String name) {
        return of(name, DEFAULT_CONFIG);
    }

    public static IErisLogger of(String name, ILoggerConfiguration config) {
        if (ERIS_INSTANCE == null) {
            throw new LoggerControllerNotLoadedException();
        }
        if (REGISTERED_LOGGER.containsKey(name)) {
            return REGISTERED_LOGGER.get(name);
        }
        IErisLogger newLogger = new ErisLogger(ERIS_INSTANCE, name, config);
        newLogger.setup();
        REGISTERED_LOGGER.put(name, newLogger);
        return newLogger;
    }
}
