package fr.eris.application;

import com.sun.net.httpserver.HttpServer;
import fr.eris.ErisWebsite;
import fr.eris.controller.web.IWebController;
import fr.eris.exception.server.ServerNotStartedException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface IErisWebApplication
{
    @NotNull ApplicationState getState();

    void load(@NotNull ErisWebsite erisInstance, @NotNull IWebController webController) throws IOException;
    void start(short webServerPort) throws IOException;
    void stop();

    HttpServer getHttpServer() throws ServerNotStartedException;
}
