import java.io.File;

import static java.io.File.*;
import static spark.Spark.*;

/**
 * Created by Adam Piech on 2017-05-04.
 */

public class Main {

    public static final String FTP_DIRECTORY_PATH = "etc" + separator + "buzzdroids" + separator + "ftp";

    public static void main(String[] args) {
        port(9000);
        get("/ftp", (req, res) -> searchFile(FTP_DIRECTORY_PATH));
    }

    private static String searchFile(String path) {
        File directory = new File(path);
        String files = "";

        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                files += file.getName() + "\n";
            }
        }

        return files;
    }
}
