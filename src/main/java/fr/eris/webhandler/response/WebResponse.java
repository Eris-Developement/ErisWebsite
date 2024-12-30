package fr.eris.webhandler.response;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WebResponse implements IWebResponse
{
    @Getter @NotNull private final Code code;
    @Getter @NotNull private final String message;

    @Getter private final int messageLength;
    @Getter private final byte @NotNull[] messageBytes;

    private WebResponse(@NotNull Code code,
                        @Nullable String message) {
        this.code = code;
        this.message = message == null ? "" : message;
        this.messageBytes = this.message.getBytes();
        this.messageLength = this.messageBytes.length;
    }

    private WebResponse(@NotNull Code code) {
        this(code, null);
    }

    public static @NotNull WebResponse of(@NotNull Code code, @Nullable String message) {
        return new WebResponse(code, message);
    }

    public static @NotNull WebResponse of(@NotNull Code code) {
        return new WebResponse(code);
    }
}
