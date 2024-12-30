package fr.eris.application;

import fr.eris.webhandler.IWebHandler;
import fr.eris.webhandler.sub.PageWebHandler;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public enum WebRoute
{
    ROOT(PageWebHandler.create("/", "web/index.html")),
    OTHER(PageWebHandler.create("/other", "web/other.html")),
    STYLE(PageWebHandler.create("/style/style.css", "web/style/style.css"));

    @Getter @NotNull private final IWebHandler handler;

    WebRoute(@NotNull IWebHandler handler) {
        this.handler = handler;
    }

    @Override
    public String toString() {
        return String.format("Route{%s}", this.getHandler().getRoute());
    }
}
