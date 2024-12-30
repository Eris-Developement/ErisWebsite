package fr.eris.handler.logger.sub;

import fr.eris.handler.logger.base.ErisLoggerHandler;
import fr.eris.util.DateFormatter;
import fr.eris.util.StringUtil;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class ErisConsoleLoggerHandler extends ErisLoggerHandler
{
    private static final Map<Level, AttributedString> STYLISED_LEVEL = new HashMap<>();
    private static int LONGEST_LEVEL = Integer.MIN_VALUE;

    static {
        STYLISED_LEVEL.put(Level.CONFIG, new AttributedString("[CONFIG]", AttributedStyle.DEFAULT.background(AttributedStyle.YELLOW)));

        STYLISED_LEVEL.put(Level.FINE, new AttributedString("[FINE]", AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN)));
        STYLISED_LEVEL.put(Level.FINER, new AttributedString("[FINER]", AttributedStyle.BOLD.foreground(AttributedStyle.GREEN)));
        STYLISED_LEVEL.put(Level.FINEST, new AttributedString("[FINEST]", AttributedStyle.BOLD.foreground(AttributedStyle.GREEN)));

        STYLISED_LEVEL.put(Level.INFO, new AttributedString("[INFO]", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW)));

        STYLISED_LEVEL.put(Level.WARNING, new AttributedString("[WARNING]", AttributedStyle.BOLD.foreground(AttributedStyle.RED)));
        STYLISED_LEVEL.put(Level.SEVERE, new AttributedString("[SEVERE]", AttributedStyle.BOLD.background(AttributedStyle.RED)));

        for (AttributedString style : STYLISED_LEVEL.values()) {
            LONGEST_LEVEL = Math.max(style.length(), LONGEST_LEVEL);
        }
    }

    @Override
    public void innerPublish(LogRecord record) {
        String message = record.getMessage();

        AttributedStringBuilder builder = new AttributedStringBuilder();
        builder.append(new AttributedString(DateFormatter.of("hh:mm:ss a"), AttributedStyle.DEFAULT.foreground(AttributedStyle.WHITE)));
        builder.append(new AttributedString(" |", AttributedStyle.DEFAULT.foreground(AttributedStyle.RED)));
        builder.append(new AttributedString("-", AttributedStyle.DEFAULT.foreground(AttributedStyle.WHITE)));

        AttributedString stylisedLevel = STYLISED_LEVEL.get(record.getLevel());
        if (stylisedLevel != null) {
            builder.append(new AttributedString(StringUtil.repeat('-', Math.floor((LONGEST_LEVEL - stylisedLevel.length()) / 2.0)), AttributedStyle.DEFAULT.foreground(AttributedStyle.WHITE)));
            builder.append(stylisedLevel);
            builder.append(new AttributedString(StringUtil.repeat('-', Math.ceil((LONGEST_LEVEL - stylisedLevel.length()) / 2.0)), AttributedStyle.DEFAULT.foreground(AttributedStyle.WHITE)));
        }

        builder.append(new AttributedString("-", AttributedStyle.DEFAULT.foreground(AttributedStyle.WHITE)));
        builder.append(new AttributedString("| ", AttributedStyle.DEFAULT.foreground(AttributedStyle.RED)));
        builder.append(message);

        if (!boundedLogger.getErisInstance().getInputController().isClosed())
            boundedLogger.getErisInstance().getInputController().getLineReader().printAbove(builder.toAttributedString());
        else defaultPublish(record);
    }
}
