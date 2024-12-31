package fr.eris.choice.command;

import org.jetbrains.annotations.NotNull;
import org.jline.reader.Candidate;

public interface ICommandChoice
{
    @NotNull String getValue();
    @NotNull String getDisplay();
    @NotNull String getDescription();

    @NotNull Candidate asCandidate();
}
