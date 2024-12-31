package fr.eris.handler.web;

import com.sun.net.httpserver.HttpExchange;
import fr.eris.handler.web.response.IWebResponse;
import org.jetbrains.annotations.NotNull;

public interface IWebHandler
{
    @NotNull String getRoute();
    boolean matchRoute(@NotNull String route);

    @NotNull IWebResponse handleExchange(@NotNull HttpExchange exchange);
}
