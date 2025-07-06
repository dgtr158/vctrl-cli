package duongtran.example.metadata;

import duongtran.example.utils.DirectoryNames;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Workspace {
    private static Workspace instance;
    private Path rootPath;

    // Private constructor to prevent direct instantiation
    private Workspace() {}

    // Method to initialize the Workspace
    public static void initialize() {
        getInstance().rootPath = Paths.get(DirectoryNames.WORKING_DIRECTORY);
    }


    // Get a single instance of Workspace
    public static Workspace getInstance() {
        if (instance == null) {
            instance = new Workspace();
        }
        return instance;
    }

    // Set the root path only once
    public void setRootPath(Path rootPath) {
        if (this.rootPath == null) {
            this.rootPath = rootPath;
        }
    }

    // List all files in the workspace
    public List<String> listFiles() {
        if (rootPath == null) {
            System.out.println("Workspace root is not set.");
            return new ArrayList<>();
        }

        try (Stream<Path> stream = Files.walk(rootPath)) {

            return stream
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Error reading files: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Read the content of the file.
     *
     * @param path the file path
     * @return byte array of the file
     * @throws IOException - if cannot read the file
     */
    public byte[] readFile(String path) throws IOException {
        File file = new File(path);
        return Files.readAllBytes(file.toPath());
    }
}
