package fr.eris.placeholder;

import org.jetbrains.annotations.NotNull;

public interface IPlaceholder<T extends PlaceholderInformation>
{
    @NotNull String parse(@NotNull T information);
}
