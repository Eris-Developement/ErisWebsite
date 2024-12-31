package fr.eris.handler.web.sub.page;

import fr.eris.exception.webhandler.WebPageContentException;
import fr.eris.util.ResourceFileUtil;
import fr.eris.util.ValidateThat;
import org.jetbrains.annotations.NotNull;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlFileParser
{
    public static final @NotNull String WEB_DIRECTORY_PREFIX = "web/";
    private static final Pattern CSS_PATTERN = Pattern.compile("<link\\s+[^>]*?rel\\s*=\\s*['\"]stylesheet['\"][^>]*?href\\s*=\\s*['\"]([^\"]+)['\"][^>]*?>");
    private static final Pattern JS_PATTERN = Pattern.compile("<script\\s+src=\"([^\"]+)\"></script>");
    private static final Pattern IMG_PATTERN = Pattern.compile("<img\\s+[^>]*?src\\s*=\\s*['\"]([^\"]+\\.png)['\"][^>]*?>");

    public static @NotNull String getHtmlFile(String path) {
        path = String.format("%s%s", WEB_DIRECTORY_PREFIX, path);
        String html = ResourceFileUtil.getFileContent(path);
        ValidateThat.notNull(html, new WebPageContentException(path));

        /* CSS */
        StringBuilder finalHtml = new StringBuilder();
        Matcher matcher = CSS_PATTERN.matcher(html);

        while (matcher.find()) {
            String cssFilePath = String.format("%s%s", WEB_DIRECTORY_PREFIX, matcher.group(1));
            String cssContent = ResourceFileUtil.getFileContent(cssFilePath);

            ValidateThat.notNull(cssContent, new WebPageContentException(cssFilePath));
            matcher.appendReplacement(finalHtml, Matcher.quoteReplacement("<style>" + cssContent + "</style>"));
        }
        html = matcher.appendTail(finalHtml).toString();

        /* JS */

        finalHtml.setLength(0);
        matcher = JS_PATTERN.matcher(html);
        while (matcher.find()) {
            String jsFilePath = String.format("%s%s", WEB_DIRECTORY_PREFIX, matcher.group(1));
            String jsContent = ResourceFileUtil.getFileContent(jsFilePath);

            ValidateThat.notNull(jsContent, new WebPageContentException(jsFilePath));
            matcher.appendReplacement(finalHtml, Matcher.quoteReplacement("<script>" + jsContent + "</script>"));
        }
        html = matcher.appendTail(finalHtml).toString();

        /* IMG */

        finalHtml.setLength(0);
        matcher = IMG_PATTERN.matcher(html);
        while (matcher.find()) {
            String imageFilePath = String.format("%s%s", WEB_DIRECTORY_PREFIX, matcher.group(1));
            byte[] imageBytes = ResourceFileUtil.getFileBytes(imageFilePath);

            ValidateThat.notNull(imageBytes, new WebPageContentException(imageFilePath));
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            String base64Src = "data:image/png;base64," + base64Image;
            matcher.appendReplacement(finalHtml, Matcher.quoteReplacement("<img src=\"" + base64Src + "\" />"));
        }

        return matcher.appendTail(finalHtml).toString();
    }
}
