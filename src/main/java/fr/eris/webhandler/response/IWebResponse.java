package fr.eris.webhandler.response;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public interface IWebResponse
{
    @NotNull String getMessage();
    @NotNull Code getCode();

    int getMessageLength();
    byte @NotNull[] getMessageBytes();

    enum Code {
        OK(200);

        @Range(from = 0, to = Integer.MAX_VALUE) private final int code;

        Code(@Range(from = 0, to = Integer.MAX_VALUE) int code) {
            this.code = code;
        }

        public int code() {
            return this.code;
        }
    }
}
