package fr.eris.controller.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.eris.ErisWebsite;
import fr.eris.handler.web.IWebHandler;
import fr.eris.handler.web.response.IWebResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface IWebController extends HttpHandler
{
    @NotNull Collection<IWebHandler> getRegisteredWebHandlers();
    void registerWebHandler(@NotNull IWebHandler handler);

    @Nullable IWebHandler findWebHandler(@NotNull String route);

    void load(@NotNull ErisWebsite erisInstance);

    @NotNull IWebResponse handleUnfoundedRequestedPage(@NotNull HttpExchange exchange);
}
