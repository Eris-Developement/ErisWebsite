package fr.eris.controller.logger;

import fr.eris.ErisWebsite;

public interface ILoggerController
{
    void load(ErisWebsite erisInstance);
    void close();
}
