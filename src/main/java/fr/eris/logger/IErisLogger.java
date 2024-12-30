package fr.eris.logger;

import fr.eris.ErisWebsite;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Filter;
import java.util.logging.Level;

public interface IErisLogger
{
    void setup();
    void close();
    String getName();

    @NotNull ErisWebsite getErisInstance();

    @Nullable Filter updateFilter(@NotNull Filter filter);
    @Nullable Filter clearFilter();

    @Nullable Level updateLevel(@NotNull Level level);
    @Nullable Level clearLevel();

    void severe(String msg);
    void warning(String msg);
    void info(String msg);
    void config(String msg);
    void fine(String msg);
    void finer(String msg);
    void finest(String msg);

    void severef(String msg, Object... args);
    void warningf(String msg, Object... args);
    void infof(String msg, Object... args);
    void configf(String msg, Object... args);
    void finef(String msg, Object... args);
    void finerf(String msg, Object... args);
    void finestf(String msg, Object... args);
}
