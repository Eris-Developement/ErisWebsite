package fr.eris.controller.web;

import fr.eris.ErisWebsite;
import fr.eris.handler.web.IWebHandler;
import fr.eris.util.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface IWebController
{
    @NotNull State getState();

    @NotNull Collection<IWebHandler> getRegisteredWebHandlers();
    void registerWebHandler(@NotNull IWebHandler handler);

    @Nullable IWebHandler findWebHandler(@NotNull String route);

    void load(@NotNull ErisWebsite erisInstance);
    void start(short webServerPort);
    void stop();
}
