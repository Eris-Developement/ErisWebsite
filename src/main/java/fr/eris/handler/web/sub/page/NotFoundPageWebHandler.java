package fr.eris.handler.web.sub.page;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class NotFoundPageWebHandler extends PageWebHandler
{
    public NotFoundPageWebHandler(@NotNull String route, @NotNull String pathToIndexPage) {
        super(route, PageType.HTML, pathToIndexPage);
    }

    @Override
    public void handle(@NotNull Context context) {
        if (!context.path().equals(this.getRoute())) {
            context.redirect("/page-not-found");
            return;
        }
        super.handle(context);
    }

    public static NotFoundPageWebHandler create(@NotNull String route, @NotNull String pathToIndexPage) {
        return new NotFoundPageWebHandler(route, pathToIndexPage);
    }

    @Override
    public void register(@NotNull Javalin server) {
        server.error(404, this);
        server.get(this.getRoute(), this);
    }
}
