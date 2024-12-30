package fr.eris.webhandler.base;

import com.sun.net.httpserver.HttpExchange;
import fr.eris.controller.logger.LoggerController;
import fr.eris.webhandler.IWebHandler;
import fr.eris.webhandler.response.IWebResponse;
import lombok.Getter;

import java.io.IOException;
import java.io.OutputStream;

@Getter
public abstract class BaseWebHandler implements IWebHandler
{
    private final String route;

    public BaseWebHandler(String route) {
        this.route = route;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        LoggerController.DEFAULT.infof("{%s} - Request to '%s' from '%s'\n", route, exchange.getRequestURI().toString(), exchange.getRemoteAddress().getAddress().toString());
        IWebResponse response = handleExchange(exchange);

        exchange.sendResponseHeaders(response.getCode().code(), response.getMessageLength());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getMessageBytes());
        outputStream.close();
    }
}
