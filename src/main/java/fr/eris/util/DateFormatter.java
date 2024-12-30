package fr.eris.util;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DateFormatter
{
    private static final @NotNull Map<@NotNull String, @NotNull DateTimeFormatter> REGISTERED_FORMATTERS = new HashMap<>();

    public static String of(String format) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = REGISTERED_FORMATTERS.get(format);

        if (formatter == null) {
            formatter = DateTimeFormatter.ofPattern(format);
            REGISTERED_FORMATTERS.put(format, formatter);
        }
        return now.format(formatter);
    }
}
