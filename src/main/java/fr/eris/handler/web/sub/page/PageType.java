package fr.eris.handler.web.sub.page;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public enum PageType
{
    CSS,
    HTML((string) -> string.replaceAll(">(//s+)<", "><"));

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
