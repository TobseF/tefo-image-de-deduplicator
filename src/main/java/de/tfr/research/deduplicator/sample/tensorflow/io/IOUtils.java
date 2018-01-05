package de.tfr.research.deduplicator.sample.tensorflow.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class IOUtils {
    public static byte[] readAllBytesOrExit(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            System.err.println("Failed to read [" + path + "]: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }
}
