package fr.eris.configuration.logger;

import fr.eris.formatter.logger.base.BaseErisLoggerFormatter;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Filter;
import java.util.logging.Level;

@Getter
public class LoggerConfiguration implements ILoggerConfiguration
{
    private final @Nullable String fileHandlerFilePath;
    private final @Nullable BaseErisLoggerFormatter fileHandlerFormatter;
    private final boolean usingConsoleHandler;
    private final @Nullable BaseErisLoggerFormatter consoleHandlerFormatter;
    private final @Nullable Filter filter;
    private final @NotNull Level level;

    private LoggerConfiguration(@Nullable String fileHandlerFilePath,
                                @Nullable BaseErisLoggerFormatter fileHandlerFormatter,
                                boolean usingConsoleHandler,
                                @Nullable BaseErisLoggerFormatter consoleHandlerFormatter,
                                @Nullable Filter filter,
                                @NotNull Level level) {
        this.fileHandlerFilePath = fileHandlerFilePath;
        this.fileHandlerFormatter = fileHandlerFormatter;
        this.usingConsoleHandler = usingConsoleHandler;
        this.consoleHandlerFormatter = consoleHandlerFormatter;
        this.filter = filter;
        this.level = level;
    }

    public static LoggerConfiguration.Builder builder() {
        return new Builder();
    }

    public static class Builder
    {
        private @Nullable String fileHandlerFilePath = null;
        private @Nullable BaseErisLoggerFormatter fileHandlerFormatter = null;
        private boolean usingConsoleHandler = false;
        private @Nullable BaseErisLoggerFormatter consoleHandlerFormatter = null;
        private @Nullable Filter filter = null;
        private @NotNull Level level = Level.ALL;

        private Builder() {
        }

        public Builder usingFileHandler(@Nullable String fileHandlerFilePath, BaseErisLoggerFormatter fileHandlerFormatter) {
            this.fileHandlerFilePath = fileHandlerFilePath;
            this.fileHandlerFormatter = fileHandlerFormatter;
            return this;
        }

        public Builder usingConsoleHandler(BaseErisLoggerFormatter consoleHandlerFormatter) {
            usingConsoleHandler = true;
            this.consoleHandlerFormatter = consoleHandlerFormatter;
            return this;
        }

        public Builder usingFilter(@Nullable Filter filter) {
            this.filter = filter;
            return this;
        }

        public Builder usingLevel(@NotNull Level level) {
            this.level = level;
            return this;
        }

        public LoggerConfiguration build() {
            return new LoggerConfiguration(fileHandlerFilePath, fileHandlerFormatter,
                    usingConsoleHandler, consoleHandlerFormatter, filter, level);
        }
    }
}
