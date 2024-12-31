package fr.eris.handler.web.response;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class WebResponse implements IWebResponse
{
    @NotNull private Code code;
    @NotNull private final String message;

    private final int messageLength;
    private final byte @NotNull[] messageBytes;

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

    public @NotNull Code setCode(@NotNull Code newCode) {
        Code oldCode = this.code;

        this.code = newCode;
        return oldCode;
    }

    public static @NotNull WebResponse of(@NotNull Code code) {
        return new WebResponse(code);
    }
}
