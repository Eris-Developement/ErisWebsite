package fr.eris.webhandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.eris.webhandler.response.IWebResponse;
import org.jetbrains.annotations.NotNull;

public interface IWebHandler extends HttpHandler
{
    @NotNull String getRoute();

    @NotNull IWebResponse handleExchange(HttpExchange exchange);
}
