package fr.eris.util;

import org.jetbrains.annotations.NotNull;

public class RouteUtil
{
    public static @NotNull String simplify(@NotNull String route) {
        route = route.replaceAll("/+", "/");
        if (route.endsWith("/") && route.length() > 1)
            route = route.substring(0, route.length() - 1);
        return route;
    }
}
