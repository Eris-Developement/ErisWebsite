package fr.eris.handler.web.sub.page;

import com.sun.net.httpserver.HttpExchange;
import fr.eris.exception.webhandler.WebPageContentException;
import fr.eris.placeholder.sub.web.WebPlaceholder;
import fr.eris.placeholder.sub.web.WebPlaceholderInformation;
import fr.eris.util.ResourceFileUtil;
import fr.eris.handler.web.base.BaseWebHandler;
import fr.eris.handler.web.response.IWebResponse;
import fr.eris.handler.web.response.WebResponse;
import fr.eris.util.ValidateThat;
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

        ValidateThat.notNull(webPageContent, new WebPageContentException(pathToIndexPage));
        return pageType.condense(webPageContent);
    }

    @Override
    public @NotNull IWebResponse handleExchange(@NotNull HttpExchange exchange) {
        WebPlaceholderInformation information = WebPlaceholderInformation.create(webPageContent, exchange);

        return WebResponse.of(IWebResponse.Code.OK, PLACEHOLDER.parse(information));
    }

    public static PageWebHandler create(@NotNull String route, @NotNull PageType pageType, @NotNull String pathToIndexPage) {
        return new PageWebHandler(route, pageType, pathToIndexPage);
    }
}
