package fr.eris;

import fr.eris.controller.logger.LoggerController;

import java.io.IOException;

public class Main
{
    private static ErisWebsite erisWebsite;

    public static void main(String[] args) throws IOException {
        System.setOut(new ErisOutStream(System.out));

        erisWebsite = new ErisWebsite();
        erisWebsite.load(args);
        erisWebsite.start();

        LoggerController.DEFAULT.info("ErisWebsite successfully stopped.");
    }
}
