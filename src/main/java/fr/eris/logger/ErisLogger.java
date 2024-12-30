package fr.eris.logger;

import fr.eris.ErisWebsite;
import fr.eris.configuration.logger.ILoggerConfiguration;
import fr.eris.controller.logger.LoggerController;
import fr.eris.handler.logger.sub.ErisConsoleLoggerHandler;
import fr.eris.util.FileUtil;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.logging.*;

public class ErisLogger extends Logger implements IErisLogger
{

    private final ILoggerConfiguration config;

    @Getter private ErisConsoleLoggerHandler consoleHandler;
    @Getter private FileHandler fileHandler;

    @Getter private final @NotNull ErisWebsite erisInstance;

    public ErisLogger(@NotNull ErisWebsite erisInstance, @NotNull String name, @NotNull ILoggerConfiguration config) {
        super(name, null);
        this.config = config;
        this.erisInstance = erisInstance;
    }

    public void setup() {
        this.setUseParentHandlers(false);
        this.setLevel(config.getLevel());
        setupConsoleHandler();
        setupFileHandler();
    }

    public void close() {
        if (config.getFileHandlerFilePath() == null)
            return;

        fileHandler.close();
        this.removeHandler(fileHandler);
        fileHandler = null;
    }

    private void setupFileHandler() {
        if (config.getFileHandlerFilePath() == null)
            return;

        try {
            String target_path = config.getFileHandlerFilePath();
            target_path = LoggerController.LOGGER_PLACEHOLDER.apply(this, target_path);
            FileUtil.createDirectoriesForFile(target_path);
            fileHandler = new FileHandler(target_path, 0, 1, true);
            fileHandler.setLevel(config.getLevel());
            if (config.getFileHandlerFormatter() != null) {
                fileHandler.setFormatter(config.getFileHandlerFormatter());
                config.getFileHandlerFormatter().boundLogger(this);
            }
            if (config.getFilter() != null)
                fileHandler.setFilter(config.getFilter());
            this.addHandler(fileHandler);
        } catch (IOException exception) {
            System.err.printf("Unable to initialize ErisLogger{%s} FileHandler: %s\n", this.getName(), exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    private void setupConsoleHandler() {
        if (!config.isUsingConsoleHandler())
            return;

        consoleHandler = new ErisConsoleLoggerHandler();
        consoleHandler.boundLogger(this);
        consoleHandler.setLevel(config.getLevel());
        if (config.getConsoleHandlerFormatter() != null) {
            consoleHandler.setFormatter(config.getConsoleHandlerFormatter());
            config.getConsoleHandlerFormatter().boundLogger(this);
        }
        if (config.getFilter() != null)
            consoleHandler.setFilter(config.getFilter());
        this.addHandler(consoleHandler);
    }

    public @Nullable Filter updateFilter(@NotNull Filter filter) {
        Filter oldFilter = this.getFilter();

        this.setFilter(filter);
        return oldFilter;
    }

    public @Nullable Filter clearFilter() {
        Filter oldFilter = this.getFilter();

        this.setFilter(null);
        return oldFilter;
    }

    @Override
    public @Nullable Level updateLevel(@NotNull Level level) {
        Level oldLevel = this.getLevel();

        this.setLevel(level);
        return oldLevel;
    }

    public @Nullable Level clearLevel() {
        Level oldLevel = this.getLevel();

        this.setLevel(config.getLevel());
        return oldLevel;
    }

    public void severef(String msg, Object... args) {
        this.severe(String.format(msg, args));
    }

    public void warningf(String msg, Object... args) {
        this.warning(String.format(msg, args));
    }

    public void infof(String msg, Object... args) {
        this.info(String.format(msg, args));
    }

    public void configf(String msg, Object... args) {
        this.config(String.format(msg, args));
    }

    public void finef(String msg, Object... args) {
        this.fine(String.format(msg, args));
    }

    public void finerf(String msg, Object... args) {
        this.finer(String.format(msg, args));
    }

    public void finestf(String msg, Object... args) {
        this.finest(String.format(msg, args));
    }
}
