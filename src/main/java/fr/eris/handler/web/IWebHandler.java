package fr.eris.handler.web;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public interface IWebHandler extends Handler
{
    @NotNull String getRoute();
    boolean matchRoute(@NotNull String route);

    void handle(@NotNull Context context);

    void register(@NotNull Javalin server);
}
