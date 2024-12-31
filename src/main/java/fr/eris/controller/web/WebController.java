package fr.eris.controller.web;

import com.sun.net.httpserver.HttpExchange;
import fr.eris.ErisWebsite;
import fr.eris.controller.logger.LoggerController;
import fr.eris.exception.webhandler.WebNotLoadedYetException;
import fr.eris.handler.web.IWebHandler;
import fr.eris.handler.web.response.IWebResponse;
import fr.eris.util.RouteUtil;
import fr.eris.util.ValidateThat;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class WebController implements IWebController
{
    @Getter private @NotNull final Collection<IWebHandler> registeredWebHandlers;

    @Getter private ErisWebsite erisInstance;

    private boolean loaded;

    public WebController() {
        this.registeredWebHandlers = new ArrayList<>();
    }

    public void load(@NotNull ErisWebsite erisInstance) {
        this.erisInstance = erisInstance;
        this.loaded = true;

        for (WebRoute route : WebRoute.values()) {
            registerWebHandler(route.getHandler());
        }
    }

    @Override
    public void registerWebHandler(@NotNull IWebHandler handler) {
        ValidateThat.isTrue(loaded, new WebNotLoadedYetException());

        this.registeredWebHandlers.add(handler);
    }

    public @Nullable IWebHandler findWebHandler(@NotNull String route) {
        for (IWebHandler webHandler : registeredWebHandlers) {
            if (webHandler.matchRoute(route)) {
                return webHandler;
            }
        }
        return null;
    }

    public @NotNull IWebResponse handleUnfoundedRequestedPage(@NotNull HttpExchange exchange) {
        return WebRoute.PAGE_NOT_FOUND.getHandler().handleExchange(exchange);
    }

    private @NotNull IWebResponse processExchange(@NotNull HttpExchange exchange) {
        String requestRoute = RouteUtil.simplify(exchange.getRequestURI().getPath());
        IWebHandler webHandler = findWebHandler(requestRoute);
        IWebResponse response;

        if (webHandler == null) {
            response = handleUnfoundedRequestedPage(exchange);
            response.setCode(IWebResponse.Code.NOT_FOUND);
        } else {
            response = webHandler.handleExchange(exchange);
        }
        return response;
    }

    private void reply(@NotNull HttpExchange exchange, @NotNull IWebResponse response) throws IOException {
        exchange.sendResponseHeaders(response.getCode().code(), response.getMessageLength());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getMessageBytes());
        outputStream.close();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ValidateThat.notNull(exchange, "HttpExchange cannot be null");

        String requestRoute = exchange.getRequestURI().getPath();
        LoggerController.DEFAULT.infof("{WebController} - Request to '%s' from '%s'\n", requestRoute, exchange.getRemoteAddress().getAddress().toString());
        IWebResponse response = processExchange(exchange);
        reply(exchange, response);
    }
}
