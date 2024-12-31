package fr.eris.handler.web.response;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public interface IWebResponse
{
    @NotNull String getMessage();
    @NotNull Code getCode();
    @NotNull Code setCode(@NotNull Code code);

    int getMessageLength();
    byte @NotNull[] getMessageBytes();

    enum Code {
        OK(200),
        NOT_FOUND(404);

        @Range(from = 0, to = Integer.MAX_VALUE) private final int code;

        Code(@Range(from = 0, to = Integer.MAX_VALUE) int code) {
            this.code = code;
        }

        public int code() {
            return this.code;
        }
    }
}
