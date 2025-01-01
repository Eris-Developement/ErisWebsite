package fr.eris.handler.web.sub.page;

import fr.eris.exception.webhandler.WebPageContentException;
import fr.eris.util.ResourceFileUtil;
import fr.eris.util.ValidateThat;
import org.jetbrains.annotations.NotNull;

public class HtmlFileParser
{
    public static @NotNull String getHtmlFile(String path) {
        String html = ResourceFileUtil.getFileContent(path);
        ValidateThat.notNull(html, new WebPageContentException(path));

        return html;
    }
}
