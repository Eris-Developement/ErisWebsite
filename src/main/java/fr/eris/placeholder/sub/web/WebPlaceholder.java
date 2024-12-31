package fr.eris.placeholder.sub.web;

import fr.eris.placeholder.base.BasePlaceholder;

public class WebPlaceholder extends BasePlaceholder<WebPlaceholderInformation>
{
    @Override
    protected void registerPlaceholders() {
        registerParsingFunction("${RawUsedUrl}", information ->
            information.getRequestURI().getPath()
        );
    }
}
