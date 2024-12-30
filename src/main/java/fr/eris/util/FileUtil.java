package fr.eris.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil
{
    public static void createDirectoriesForFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Path parentDir = path.getParent();

        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }
    }
}
