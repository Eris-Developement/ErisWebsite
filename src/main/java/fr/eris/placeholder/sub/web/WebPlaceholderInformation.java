package fr.eris.placeholder.sub.web;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import fr.eris.placeholder.PlaceholderInformation;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.net.URI;

public class WebPlaceholderInformation extends PlaceholderInformation
{
    @Getter private final @NotNull HttpExchange exchange;
    @Getter private final @NotNull URI requestURI;

    private WebPlaceholderInformation(@NotNull String defaultContent, @NotNull HttpExchange exchange) {
        super(defaultContent);
        this.exchange = exchange;
        this.requestURI = exchange.getRequestURI();
    }

    public static WebPlaceholderInformation create(@NotNull String defaultContent, @NotNull HttpExchange context) {
        return new WebPlaceholderInformation(defaultContent, context);
    }
}
