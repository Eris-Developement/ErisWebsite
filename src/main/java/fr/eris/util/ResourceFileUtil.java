package fr.eris.util;

import fr.eris.controller.logger.LoggerController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ResourceFileUtil
{
    public static InputStream getStream(String path) {
        ClassLoader classLoader = ResourceFileUtil.class.getClassLoader();
        InputStream stream = classLoader.getResourceAsStream(path);

        if (stream == null) {
            throw new IllegalArgumentException("Resource file not found: " + path);
        }

        return stream;
    }

    public static String getFileContent(String path) {
        StringBuilder content = new StringBuilder();

        try (InputStream stream = getStream(path);
             InputStreamReader streamReader = new InputStreamReader(stream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException exception) {
            LoggerController.DEFAULT.severef("Unable to open get file content.\n%s", exception.toString());
            return null;
        }
        return content.toString();
    }
}
