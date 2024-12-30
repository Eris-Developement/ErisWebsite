package fr.eris.util;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil
{
    public static final Pattern STRING_TO_SENTENCE_PATTERN = Pattern.compile("(\"+.*?\"+)|('+.*?'+)|\\S+");

    public static @NotNull String repeat(String str, Number count) {
        return str.repeat(Math.max(0, count.intValue()));
    }

    public static @NotNull String repeat(char str, Number count) {
        return String.valueOf(str).repeat(Math.max(0, count.intValue()));
    }

    public static void simplifyStringList(List<String> list) {
        list.replaceAll(StringUtil::simplifyString);
    }

    public static @NotNull List<String> splitStringToSentence(String str) {
        List<String> result = new ArrayList<>();
        Matcher matcher = STRING_TO_SENTENCE_PATTERN.matcher(str);

        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }

    public static @NotNull String simplifyString(String string) {
        if (string.length() > 1 && (string.startsWith("\"") && string.endsWith("\"")) ||
                (string.startsWith("'") && string.endsWith("'"))) {
            return string.substring(1, string.length() - 1);
        }
        return string;
    }

    public static @NotNull List<String> createArgumentsList(List<String> inputData, String input) {
        List<String> arguments = new ArrayList<>(inputData);

        if (arguments.isEmpty())
            return arguments;

        arguments.removeFirst();
        if (input.endsWith(" "))
            arguments.add("");
        return arguments;
    }
}
