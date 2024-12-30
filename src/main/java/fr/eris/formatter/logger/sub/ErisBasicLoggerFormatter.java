package fr.eris.formatter.logger.sub;

import fr.eris.formatter.logger.base.BaseErisLoggerFormatter;
import fr.eris.util.DateFormatter;
import org.jetbrains.annotations.NotNull;

import java.util.logging.LogRecord;

public class ErisBasicLoggerFormatter extends BaseErisLoggerFormatter
{
    public String innerFormat(@NotNull LogRecord record) {
        return String.format("At:%s | Type:%s | From:%s | By:%s | %s\n",
                DateFormatter.of("hh:mm:ss a"),
                record.getLevel().getName(),
                Thread.currentThread().getName(),
                boundedLogger.getName(),
                record.getMessage());
    }
}
