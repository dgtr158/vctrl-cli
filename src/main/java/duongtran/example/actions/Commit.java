package duongtran.example.actions;

import duongtran.example.metadata.Workspace;
import duongtran.example.storage.ObjectStorage;
import duongtran.example.storage.objects.Blob;
import duongtran.example.storage.Database;
import duongtran.example.storage.objects.Entry;
import duongtran.example.storage.objects.Tree;
import duongtran.example.utils.DirectoryNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Commit {

    private static final Logger logger = LoggerFactory.getLogger(Commit.class);

    private static final String ROOT_PATH = DirectoryNames.WORKING_DIRECTORY;
    private final Workspace workspace;
    private final Database database;

    public Commit() {
        this.workspace = Workspace.getInstance();
        this.database = initializeDatabase();
    }

    /**
     * Initializes and returns a new instance of the Database.
     * The database is configured to use a path derived from the root directory
     * and the objects directory.
     *
     * @return a new Database instance configured with the appropriate path.
     */
    private Database initializeDatabase() {
        File gitPath = new File(ROOT_PATH, DirectoryNames.ROOT_DIR_NAME);
        File dbPath = new File(gitPath, DirectoryNames.OBJECTS);
        return new Database(dbPath.toPath());
    }

    /**
     * Executes the commit operation for the workspace. This method commits all
     * files stored in the workspace by calling {@code storeWorkspaceFiles} and logs
     * the successfully committed files.
     *
     * If an error occurs during the process, an {@link IOException} is thrown with
     * a descriptive message and the underlying exception as its cause.
     *
     * @throws IOException if there is an error during the commit process, such as
     *                     issues with reading files, hashing, or storing data.
     */
    public void execute() throws IOException {
        try {
            storeWorkspaceFiles();
            logger.info("Successfully committed files: {}", workspace.listFiles());
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new IOException("Failed to commit changes", e);
        }
    }

    /**
     * Stores all files from the workspace into the database. This method retrieves
     * the list of file paths from the workspace, reads the content of each file,
     * creates a {@link Blob} object for the file's data, and stores it in the database.
     *
     * Files contained in the workspace are iterated, and only regular files
     * are processed. For each valid file, its content is read
     * as a byte array and encapsulated in a {@link Blob} object, which is then
     * stored in the database using the {@code Database.store} method.
     *
     * @throws IOException if an error occurs while listing, reading, or storing files.
     * @throws NoSuchAlgorithmException if a required hashing algorithm is unavailable
     *                                  during the blob storage process.
     */
    private void storeWorkspaceFiles() throws IOException, NoSuchAlgorithmException {
        List<String> filePaths = workspace.listFiles();
        List<Entry> entries = new ArrayList<>();

        for (String path : filePaths) {
            File file = new File(path);
            if (!file.isDirectory()) {
                byte[] data = workspace.readFile(path);
                ObjectStorage<byte[]> blob = new Blob(data);
                database.store(blob);
                entries.add(new Entry(
                        file.getName(), blob.getOid()
                ));
            } else {
                // TODO: Handle directory
                // Recursively walk through the directory, storing blob and tree if needed
            }
        }

        Tree tree = new Tree(entries);
        database.store(tree);

    }


}
