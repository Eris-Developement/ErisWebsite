package fr.eris.formatter.logger.base;

import fr.eris.exception.logger.FormatterNotBoundException;
import fr.eris.logger.IErisLogger;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public abstract class BaseErisLoggerFormatter extends Formatter
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

    public final String format(@NotNull LogRecord record) {
        if (boundedLogger == null)
            throw new FormatterNotBoundException();

        return innerFormat(record);
    }

    public abstract String innerFormat(@NotNull LogRecord record);
}
