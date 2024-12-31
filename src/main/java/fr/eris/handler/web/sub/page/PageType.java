package fr.eris.handler.web.sub.page;

import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public enum PageType
{
    ICO,
    CSS((string) ->
            string.replaceAll("/\\*[^*]*\\*+([^/*][^*]*\\*+)*/", "")
                  .replaceAll("\\s*([{}:;,])\\s*", "$1")
                  .replaceAll("\\s+", " ")
                  .replaceAll("^\\s+|\\s+$", "")
    ),
    JS((string) -> string.replaceAll("//.*", "")
                               .replaceAll("/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/", "")
                               .replaceAll("\\s*([{};,:()=+*/<>|&%!?])\\s*", "$1")
                               .replaceAll("\\s{2,}", " ")
                               .replaceAll("\\s*\\n\\s*", "")
    ),
    HTML((string) -> string.replaceAll("<!--(?!\\[if).*?-->", "")
                                 .replaceAll(">\\s+<", "><")
                                 .replaceAll("\\s{2,}", " ")
                                 .replaceAll("^\\s+|\\s+$", "")
    );

    @Nullable private final Function<String, String> condenseFunction;

    PageType(@Nullable Function<String, String> condenseFunction) {
        this.condenseFunction = condenseFunction;
    }

    PageType() {
        this(null);
    }

    public String condense(String text) {
        if (this.condenseFunction == null) {
            return text;
        }
        return condenseFunction.apply(text);
    }
}
