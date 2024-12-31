package fr.eris.placeholder.sub;

import fr.eris.placeholder.PlaceholderInformation;
import fr.eris.placeholder.base.BasePlaceholder;

public class EmptyPlaceholder<T extends PlaceholderInformation> extends BasePlaceholder<T>
{
    @Override
    protected void registerPlaceholders() {
    }
}
