package fr.eris.handler.web.base;

import fr.eris.handler.web.IWebHandler;
import fr.eris.util.RouteUtil;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class BaseWebHandler implements IWebHandler
{
    private final @NotNull String route;

    public BaseWebHandler(@NotNull String route) {
        this.route = RouteUtil.simplify(route);
    }

    @Override
    public boolean matchRoute(@NotNull String route) {
        return this.route.equals(route);
    }
}
