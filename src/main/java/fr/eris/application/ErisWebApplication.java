package fr.eris.application;

import com.sun.net.httpserver.HttpServer;
import fr.eris.ErisWebsite;
import fr.eris.controller.logger.LoggerController;
import fr.eris.webhandler.IWebHandler;
import fr.eris.exception.server.ServerNotLoadedException;
import fr.eris.exception.server.ServerNotStartedException;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ErisWebApplication implements IErisWebApplication
{
    @Nullable private HttpServer server;

    @Getter private ApplicationState state;
    @Getter private ExecutorService executor;

    private ErisWebsite erisInstance;

    public ErisWebApplication() {
        this.state = ApplicationState.STOPPED;
    }

    private void loadContext(@NotNull HttpServer server) {
        @NotNull IWebHandler handler;

        for (@NotNull WebRoute route : WebRoute.values()) {
            LoggerController.DEFAULT.info("Loading route: " + route);
            handler = route.getHandler();
            server.createContext(handler.getRoute(), handler);
        }
    }

    public void load(ErisWebsite erisInstance) throws IOException {
        this.erisInstance = erisInstance;
        this.server = HttpServer.create();
        LoggerController.DEFAULT.info("Loading web server.");
        this.state = ApplicationState.LOADING;

        loadContext(this.server);
        this.executor = Executors.newSingleThreadExecutor();
        this.server.setExecutor(executor);
        this.state = ApplicationState.LOADED;
    }

    public void start(short webServerPort) throws IOException {
        if (this.server == null || this.state != ApplicationState.LOADED) {
            throw new ServerNotLoadedException();
        }
        this.state = ApplicationState.STARTING;
        this.server.bind(new InetSocketAddress(webServerPort), 0);

        this.server.start();
        LoggerController.DEFAULT.info("ErisWebApplication started on port: " + webServerPort);
        this.state = ApplicationState.STARTED;
    }

    private void waitForServerClosure() {
        while (this.state == ApplicationState.STARTED) {
            Thread.onSpinWait();
        }
    }

    public void stop() {
        if (this.state != ApplicationState.STARTED) {
            throw new ServerNotStartedException();
        }
        HttpServer server = getHttpServer();
        LoggerController.DEFAULT.info("Stopping web server...");
        this.state = ApplicationState.STOPPING;

        server.stop(0);
        executor.close();
        this.state = ApplicationState.STOPPED;
        LoggerController.DEFAULT.info("Web is now server stopped.");
    }

    @Override
    public @NotNull HttpServer getHttpServer() throws ServerNotStartedException {
        if (this.state != ApplicationState.STARTED || this.server == null) {
            throw new ServerNotStartedException();
        }
        return this.server;
    }
}
