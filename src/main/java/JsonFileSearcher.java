import com.amazonaws.util.IOUtils;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam Piech on 2017-05-05.
 */

public class JsonFileSearcher {

    public List<File> searchFile(String path) {
        List<File> files = new ArrayList<>();
        File directory = new File(path);

        for (File file : directory.listFiles()) {
            if (file.isFile() && Files.getFileExtension(file.getName()).equals(States.JSON_FILE_EXTENSION)) {
                files.add(file);
            }
        }

        return files;
    }

    public JsonObject convertToJson(File file) {
        if (file.exists()){
            try(InputStreamReader stream = new InputStreamReader(new FileInputStream(file.getPath()))) {
                JsonParser parser = new JsonParser();
                return (JsonObject) parser.parse(CharStreams.toString(stream));
            } catch (FileNotFoundException e) {
            } catch (IOException e) {}
        }
        return null;
    }

}
