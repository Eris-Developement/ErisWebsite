package fr.eris.controller.input;

import org.jline.reader.Highlighter;
import org.jline.reader.LineReader;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import java.util.regex.Pattern;

public class ErisInputHighlighter implements Highlighter
{
    @Override
    public AttributedString highlight(LineReader lineReader, String buffer) {
        AttributedStringBuilder attributedStringBuilder = new AttributedStringBuilder();
        attributedStringBuilder.append(new AttributedString(buffer, AttributedStyle.BOLD.foreground(AttributedStyle.BLUE)));
        return attributedStringBuilder.toAttributedString();
    }

    @Override
    public void setErrorPattern(Pattern pattern) {

    }

    @Override
    public void setErrorIndex(int i) {

    }
}
