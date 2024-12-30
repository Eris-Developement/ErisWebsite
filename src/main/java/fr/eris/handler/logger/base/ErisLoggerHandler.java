package fr.eris.handler.logger.base;

import fr.eris.exception.logger.FormatterNotBoundException;
import fr.eris.logger.IErisLogger;
import org.jetbrains.annotations.NotNull;

import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;

public abstract class ErisLoggerHandler extends ConsoleHandler
{
    protected IErisLogger boundedLogger;

    public @NotNull IErisLogger getBoundedLogger() {
        if (boundedLogger == null)
            throw new FormatterNotBoundException();
        return boundedLogger;
    }

    public void boundLogger(@NotNull IErisLogger newBoundedLogger) {
        boundedLogger = newBoundedLogger;
    }

    public final void publish(@NotNull LogRecord record) {
        if (boundedLogger == null)
            throw new FormatterNotBoundException();

        innerPublish(record);
    }

    public final void defaultPublish(@NotNull LogRecord record) {
        super.publish(record);
    }

    public abstract void innerPublish(LogRecord record);
}
