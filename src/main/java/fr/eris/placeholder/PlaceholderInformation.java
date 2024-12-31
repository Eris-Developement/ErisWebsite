package fr.eris.placeholder;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public abstract class PlaceholderInformation
{
    @Getter protected final @NotNull String defaultContent;

    protected PlaceholderInformation(@NotNull String defaultContent) {
        this.defaultContent = defaultContent;
    }
}
