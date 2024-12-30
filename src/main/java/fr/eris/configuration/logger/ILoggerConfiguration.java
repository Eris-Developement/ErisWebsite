package fr.eris.configuration.logger;

import fr.eris.formatter.logger.base.BaseErisLoggerFormatter;
import fr.eris.formatter.logger.sub.ErisBasicLoggerFormatter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Level;

public interface ILoggerConfiguration
{
    @Nullable String getFileHandlerFilePath();
    @Nullable BaseErisLoggerFormatter getFileHandlerFormatter();
    @Nullable Filter getFilter();

    @NotNull Level getLevel();

    boolean isUsingConsoleHandler();
    @Nullable BaseErisLoggerFormatter getConsoleHandlerFormatter();
}
