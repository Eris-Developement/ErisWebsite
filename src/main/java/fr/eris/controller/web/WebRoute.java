package fr.eris.controller.web;

import fr.eris.handler.web.IWebHandler;
import fr.eris.handler.web.sub.page.PageWebHandler;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import static fr.eris.handler.web.sub.page.PageType.*;

@Getter
public enum WebRoute
{
    PAGE_NOT_FOUND(PageWebHandler.create("/page_not_found", HTML, "page_not_found.html")),
    ROOT(PageWebHandler.create("/", HTML, "index.html")),
    OTHER(PageWebHandler.create("/other", HTML, "other.html"));

    @NotNull private final IWebHandler handler;

    WebRoute(@NotNull IWebHandler handler) {
        this.handler = handler;
    }

    @Override
    public String toString() {
        return String.format("Route{%s}", this.getHandler().getRoute());
    }
}
