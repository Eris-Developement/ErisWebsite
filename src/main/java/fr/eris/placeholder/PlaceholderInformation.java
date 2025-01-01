package fr.eris.placeholder;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class PlaceholderInformation
{
    protected final @NotNull String defaultContent;

    protected PlaceholderInformation(@NotNull String defaultContent) {
        this.defaultContent = defaultContent;
    }
}
