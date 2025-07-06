package duongtran.example;

import duongtran.example.actions.Commit;
import duongtran.example.actions.Init;
import duongtran.example.metadata.Workspace;
import duongtran.example.utils.ActionConstants;
import duongtran.example.utils.DirectoryNames;

import java.io.IOException;
import java.text.MessageFormat;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length < 1 || args.length > 3) {
            System.out.println("USAGE: java Main.java init [file_path]");
            return;
        }

        // Get the first argument
        String command = args[0];

        // Initialize Workspace at startup
        Workspace.initialize();

        switch (command) {
            case ActionConstants.INIT:
                String basePath = args.length == 2 ? args[1] : null;
                Init.init(basePath);
                break;
            case ActionConstants.COMMIT:
                Commit commit = new Commit();
                commit.execute();
                break;
            default:
                String msg = MessageFormat.format("{0}: {1} is not a {2} command"
                        , DirectoryNames.PROJECT_NAME
                        , command
                        , DirectoryNames.PROJECT_NAME
                );
                System.out.println(msg);
        }
    }
}