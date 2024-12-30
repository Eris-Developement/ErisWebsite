package fr.eris;

import fr.eris.controller.logger.LoggerController;

import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException {
        System.setOut(new ErisOutStream(System.out));

        ErisWebsite erisWebsite = new ErisWebsite();
        erisWebsite.load(args);
        erisWebsite.start();

        LoggerController.DEFAULT.info("ErisWebsite successfully stopped.");
    }
}
