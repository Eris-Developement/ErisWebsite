package fr.eris.controller.web;

import fr.eris.handler.web.IWebHandler;
import fr.eris.handler.web.sub.page.PageWebHandler;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import static fr.eris.handler.web.sub.page.PageType.*;

@Getter
public enum WebRoute
{
    PAGE_NOT_FOUND(PageWebHandler.create("", HTML, "web/page_not_found.html")),
    PAGE_NOT_FOUND_CSS(PageWebHandler.create("/style/page_not_found.css", CSS, "web/style/page_not_found.css")),
    PAGE_NOT_FOUND_JS(PageWebHandler.create("/script/page_not_found.js", JS, "web/script/page_not_found.js")),
    ROOT(PageWebHandler.create("/", HTML, "web/index.html")),
    OTHER(PageWebHandler.create("/other", HTML, "web/other.html")),
    STYLE(PageWebHandler.create("/style/style.css", CSS, "web/style/style.css"));

    @NotNull private final IWebHandler handler;

    WebRoute(@NotNull IWebHandler handler) {
        this.handler = handler;
    }

    @Override
    public String toString() {
        return String.format("Route{%s}", this.getHandler().getRoute());
    }
}