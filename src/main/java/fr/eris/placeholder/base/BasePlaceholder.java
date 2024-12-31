package fr.eris.placeholder.base;

import fr.eris.exception.placeholder.PlaceholderAlreadyRegisteredException;
import fr.eris.placeholder.IPlaceholder;
import fr.eris.placeholder.PlaceholderInformation;
import fr.eris.util.ValidateThat;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class BasePlaceholder<T extends PlaceholderInformation> implements IPlaceholder<T>
{
    private final @NotNull Map<String, Function<T, String>> parsingFunction;

    protected BasePlaceholder() {
        this.parsingFunction = new HashMap<>();
        registerPlaceholders();
    }

    protected void registerParsingFunction(@NotNull String placeholder, @NotNull Function<T, String> function) {
        ValidateThat.mapDontContainKey(this.parsingFunction, placeholder, new PlaceholderAlreadyRegisteredException(placeholder));
        this.parsingFunction.put(placeholder, function);
    }

    protected abstract void registerPlaceholders();

    @Override
    public @NotNull String parse(@NotNull T information) {
        String result = information.getDefaultContent();

        for (Map.Entry<String, Function<T, String>> entry : parsingFunction.entrySet()) {
            String key = entry.getKey();
            Function<T, String> function = entry.getValue();

            result = result.replace(key, function.apply(information));
        }
        return result;
    }
}
