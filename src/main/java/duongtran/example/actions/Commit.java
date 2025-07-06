package duongtran.example.actions;

import duongtran.example.metadata.Workspace;
import duongtran.example.storage.Blob;
import duongtran.example.storage.Database;
import duongtran.example.utils.DirectoryNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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

    private Database initializeDatabase() {
        File gitPath = new File(ROOT_PATH, DirectoryNames.ROOT_DIR_NAME);
        File dbPath = new File(gitPath, DirectoryNames.OBJECTS);
        return new Database(dbPath.toPath());
    }

    public void execute() throws IOException {
        try {
            storeWorkspaceFiles();
            logger.info("Successfully committed files: {}", workspace.listFiles());
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new IOException("Failed to commit changes", e);
        }
    }

    private void storeWorkspaceFiles() throws IOException, NoSuchAlgorithmException {
        List<String> filePaths = workspace.listFiles();
        for (String path : filePaths) {
            storeFileIfRegular(path);
        }
    }

    private void storeFileIfRegular(String path) throws IOException, NoSuchAlgorithmException {
        File file = new File(path);
        if (!file.isDirectory()) {
            byte[] data = workspace.readFile(path);
            Blob blob = new Blob(data);
            database.store(blob);
        }
    }


}
