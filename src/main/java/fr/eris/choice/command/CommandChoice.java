package fr.eris.choice.command;

import fr.eris.command.ICommand;
import fr.eris.util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jline.reader.Candidate;

public class CommandChoice implements ICommandChoice
{

    @Getter private final @NotNull String value;
    @Getter private final @NotNull String display;
    @Getter private final @Nullable String description;

    private CommandChoice(@NotNull String value,
                          @Nullable String display,
                          @Nullable String description) {
        this.value = value;
        this.display = display == null ? value : display;
        this.description = description;
    }

    public @NotNull Candidate asCandidate() {
        return new Candidate(value, display,
                null, description,
                null, null, true);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String value;
        private String display;
        private String description;

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder display(String display) {
            this.display = display;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public CommandChoice build() {
            if (value == null)
                throw new IllegalArgumentException("value cannot be null");
            return new CommandChoice(value, display, description);
        }
    }
}
