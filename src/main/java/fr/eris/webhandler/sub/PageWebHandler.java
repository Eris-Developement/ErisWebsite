package fr.eris.webhandler.sub;

import com.sun.net.httpserver.HttpExchange;
import fr.eris.exception.webhandler.WebPageContentException;
import fr.eris.util.ResourceFileUtil;
import fr.eris.webhandler.base.BaseWebHandler;
import fr.eris.webhandler.response.IWebResponse;
import fr.eris.webhandler.response.WebResponse;
import org.jetbrains.annotations.NotNull;

public class PageWebHandler extends BaseWebHandler
{
    public final String pathToIndexPage;
    public final String webPageContent;

    private PageWebHandler(String route, String pathToIndexPage) {
        super(route);
        this.pathToIndexPage = pathToIndexPage;
        this.webPageContent = ResourceFileUtil.getFileContent(pathToIndexPage);

        if (this.webPageContent == null) {
            throw new WebPageContentException(pathToIndexPage);
        }
    }

    @Override
    public @NotNull IWebResponse handleExchange(HttpExchange exchange) {
        return WebResponse.of(IWebResponse.Code.OK, webPageContent);
    }

    public static PageWebHandler create(String route, String pathToIndexPage) {
        return new PageWebHandler(route, pathToIndexPage);
    }
}
