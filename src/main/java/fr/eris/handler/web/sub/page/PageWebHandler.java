package fr.eris.handler.web.sub.page;

import fr.eris.exception.webhandler.WebPageContentException;
import fr.eris.placeholder.sub.web.WebPlaceholder;
import fr.eris.placeholder.sub.web.WebPlaceholderInformation;
import fr.eris.handler.web.base.BaseWebHandler;
import fr.eris.util.ResourceFileUtil;
import fr.eris.util.ValidateThat;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class PageWebHandler extends BaseWebHandler
{
    @NotNull protected final String pathToIndexPage;
    @NotNull protected final String webPageContent;
    @NotNull protected final PageType pageType;

    private static final WebPlaceholder PLACEHOLDER = new WebPlaceholder();

    protected PageWebHandler(@NotNull String route, @NotNull PageType pageType, @NotNull String pathToIndexPage) {
        super(route);
        this.pathToIndexPage = pathToIndexPage;
        this.pageType = pageType;
        this.webPageContent = loadPageContent(pageType, pathToIndexPage);
    }

    private @NotNull String loadPageContent(@NotNull PageType pageType, @NotNull String pathToIndexPage) {
        String webPageContent = ResourceFileUtil.getFileContent(pathToIndexPage);
        ValidateThat.notNull(webPageContent, new WebPageContentException(webPageContent));

        ValidateThat.notNull(webPageContent, new WebPageContentException(pathToIndexPage));
        return pageType.condense(webPageContent);
    }

    @Override
    public void handle(@NotNull Context context) {
        WebPlaceholderInformation information = WebPlaceholderInformation.create(webPageContent, context);

        context.html(PLACEHOLDER.parse(information));
    }

    public static PageWebHandler create(@NotNull String route, @NotNull PageType pageType, @NotNull String pathToIndexPage) {
        return new PageWebHandler(route, pageType, pathToIndexPage);
    }

    @Override
    public void register(@NotNull Javalin server) {
        server.get(this.getRoute(), this);
    }
}
