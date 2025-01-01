package fr.eris.placeholder.sub.web;

import fr.eris.placeholder.PlaceholderInformation;
import io.javalin.http.Context;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class WebPlaceholderInformation extends PlaceholderInformation
{
    private final @NotNull Context context;
    private final @NotNull String requestIp;

    private WebPlaceholderInformation(@NotNull String defaultContent, @NotNull Context context) {
        super(defaultContent);
        this.context = context;
        this.requestIp = context.ip();
    }

    public static WebPlaceholderInformation create(@NotNull String defaultContent, @NotNull Context context) {
        return new WebPlaceholderInformation(defaultContent, context);
    }
}
