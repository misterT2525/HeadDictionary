package net.mistert2525.headdictionary.utils;

import lombok.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author misterT2525
 */
public final class ResourcesUtils {

    private ResourcesUtils() {
        throw new RuntimeException("Non instantiable class");
    }

    public static void copyDirectory(@NonNull String from, @NonNull File to) throws IOException {
        from = from.startsWith("/") ? from.substring(1) : from;
        from = from.endsWith("/") ? from : from + "/";

        if (to.exists() || !to.mkdirs()) {
            throw new IOException("Can not create directory");
        }

        File file = new File(ResourcesUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        if (!file.isFile()) {
            throw new IOException(file.getAbsolutePath() + "not found");
        }

        JarFile jar = new JarFile(file);
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (!entry.getName().startsWith(from) || entry.getName().equals(from)) {
                continue;
            }

            String path = entry.getName().substring(from.length()).replace("/", File.separator);
            File toFile = new File(to, path);

            InputStream in = jar.getInputStream(entry);
            Files.copy(in, toFile.toPath());
        }
    }
}
