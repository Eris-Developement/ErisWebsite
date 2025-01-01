package fr.eris.controller.web;

import fr.eris.ErisWebsite;
import fr.eris.exception.webhandler.WebNotLoadedYetException;
import fr.eris.handler.web.IWebHandler;
import fr.eris.util.State;
import fr.eris.util.ValidateThat;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class WebController implements IWebController
{
    @Getter private @NotNull final Collection<IWebHandler> registeredWebHandlers;
    private Javalin server;

    @Getter private ErisWebsite erisInstance;

    @Getter private @NotNull State state;

    public WebController() {
        this.registeredWebHandlers = new ArrayList<>();
        this.state = State.STOPPED;
    }

    public void load(@NotNull ErisWebsite erisInstance) {
        this.erisInstance = erisInstance;
        this.state = State.LOADING;

        this.server = Javalin.create((config) ->  {
            config.staticFiles.add("/web", Location.CLASSPATH);
        });
        for (WebRoute webRoute : WebRoute.values()) {
            registerWebHandler(webRoute.getHandler());
        }
        this.state = State.LOADED;
    }

    @Override
    public void registerWebHandler(@NotNull IWebHandler handler) {
        ValidateThat.notNull(this.server, new WebNotLoadedYetException());

        this.registeredWebHandlers.add(handler);
        handler.register(server);
    }

    public void start(short webServerPort) {
        ValidateThat.notNull(this.server, new WebNotLoadedYetException());

        this.state = State.STARTING;
        this.server.start(webServerPort);
        this.state = State.STARTED;
    }

    public @Nullable IWebHandler findWebHandler(@NotNull String route) {
        for (IWebHandler webHandler : registeredWebHandlers) {
            if (webHandler.matchRoute(route)) {
                return webHandler;
            }
        }
        return null;
    }

    public void stop() {
        ValidateThat.notNull(this.server, new WebNotLoadedYetException());

        this.state = State.STOPPING;
        this.server.stop();
        this.state = State.STOPPED;
    }
}
