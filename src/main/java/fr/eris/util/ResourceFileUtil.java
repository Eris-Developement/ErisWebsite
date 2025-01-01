package fr.eris.util;

import fr.eris.controller.logger.LoggerController;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
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

    public static @Nullable String getFileContent(@NotNull String path) {
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

    public static byte[] getFileBytes(String path) {
        try (InputStream stream = getStream(path);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = stream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException exception) {
            LoggerController.DEFAULT.severef("Unable to open get file bytes.\n%s", exception.toString());
            return null;
        }
    }
}
