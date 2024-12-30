package fr.eris.application;

import com.sun.net.httpserver.HttpServer;
import fr.eris.ErisWebsite;
import fr.eris.exception.server.ServerNotStartedException;

import java.io.IOException;

public interface IErisWebApplication
{
    ApplicationState getState();

    void load(ErisWebsite erisInstance) throws IOException;
    void start(short webServerPort) throws IOException;
    void stop();

    HttpServer getHttpServer() throws ServerNotStartedException;
}
